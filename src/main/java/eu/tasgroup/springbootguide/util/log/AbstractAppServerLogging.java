package eu.tasgroup.springbootguide.util.log;


import eu.tasgroup.springbootguide.controller.model.ComponentEnum;
import eu.tasgroup.springbootguide.exception.AppErrorCodeMessageEnum;
import eu.tasgroup.springbootguide.exception.AppException;
import eu.tasgroup.springbootguide.util.AppConstant;
import eu.tasgroup.springbootguide.util.ParamsUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Getter
@Setter
@Slf4j
public abstract class AbstractAppServerLogging implements HandlerInterceptor {

  public static final String REQUEST_DEFAULT_MESSAGE_PREFIX = "=> Request SERVER ID=%s [";
  public static final String REQUEST_DEFAULT_MESSAGE_SUFFIX = "]";
  public static final String RESPONSE_DEFAULT_MESSAGE_PREFIX = "<= Response SERVER ID=%s [";
  public static final String RESPONSE_DEFAULT_MESSAGE_SUFFIX = "] elapsed=%dms";
  private static final int REQUEST_DEFAULT_MAX_PAYLOAD_LENGTH = 50;
  private static final int RESPONSE_DEFAULT_MAX_PAYLOAD_LENGTH = 50;

  private boolean requestIncludeClientInfo = false;

  private boolean requestIncludeHeaders = false;

  private boolean responseIncludeHeaders = false;

  private boolean requestIncludePayload = false;

  private boolean responseIncludePayload = false;

  private Predicate<String> requestHeaderPredicate;

  private Predicate<String> responseHeaderPredicate;

  private int requestMaxPayloadLength = REQUEST_DEFAULT_MAX_PAYLOAD_LENGTH;

  private int responseMaxPayloadLength = RESPONSE_DEFAULT_MAX_PAYLOAD_LENGTH;

  private boolean requestToDB = false;

  private boolean responseToDB = false;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    long start = System.nanoTime();
    String id = UUID.randomUUID().toString();
    MDC.put(AppConstant.MDC_COMPONENT, ComponentEnum.HTTP.name());
    MDC.put(AppConstant.MDC_SERVER_ID, id);
    MDC.put(AppConstant.MDC_START, start + "");
    final Map<String, String> pathVariables =
        (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    String callerID =
        pathVariables.getOrDefault(AppConstant.CALLER_ID_KEY, AppConstant.NOT_AVAILABLE);
    if(callerID.length()>255){
      throw new AppException(AppErrorCodeMessageEnum.BAD_REQUEST);
    }
    boolean shouldRequestLog = shouldRequestLog(request);
    if (shouldRequestLog) {
      request(request, getRequestMessage(id, request));
    }
    requestDB(id, request, callerID);

    return true;
  }

  @Override
  public void afterCompletion(
          HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    String id = MDC.get(AppConstant.MDC_SERVER_ID);
    String iuv = MDC.get(AppConstant.MDC_IUV);
    String callerId = MDC.get(AppConstant.MDC_CALLERID);
    String paFiscalCode = MDC.get(AppConstant.MDC_PA_FISCAL_CODE);
    long start = Long.parseLong(MDC.get(AppConstant.MDC_START));
    long end = System.nanoTime();
    long elapsed = TimeUnit.NANOSECONDS.toMillis(end - start);

    boolean shouldResponseLog = shouldResponseLog(response);
    if (shouldResponseLog) {
      response(response, getResponseMessage(id, elapsed, response));
    }
    responseDB(id, elapsed, response, callerId, iuv, paFiscalCode);
  }

  private String getRequestMessage(String id, HttpServletRequest request) {
    return createRequestMessage(id, request);
  }

  private String getResponseMessage(String id, Long elapsedMillis, HttpServletResponse response) {
    return createResponseMessage(id, elapsedMillis, response);
  }

  public String createRequestMessage(String id, HttpServletRequest request) {
    StringBuilder msg = new StringBuilder();
    msg.append(String.format(REQUEST_DEFAULT_MESSAGE_PREFIX, id));
    msg.append(request.getMethod()).append(' ');
    msg.append(request.getRequestURI());

    String queryString = request.getQueryString();
    if (queryString != null) {
      msg.append('?').append(queryString);
    }

    if (isRequestIncludeClientInfo()) {
      String client = request.getRemoteAddr();
      if (StringUtils.hasLength(client)) {
        msg.append(", client=").append(client);
      }
      HttpSession session = request.getSession(false);
      if (session != null) {
        msg.append(", session=").append(session.getId());
      }
      String user = request.getRemoteUser();
      if (user != null) {
        msg.append(", user=").append(user);
      }
    }

    if (isRequestIncludeHeaders()) {
      HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
      if (getRequestHeaderPredicate() != null) {
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
          String header = names.nextElement();
          if (!getRequestHeaderPredicate().test(header)) {
            headers.set(header, "masked");
          }
        }
      }
      msg.append(", headers=").append(ParamsUtils.formatHeaders(headers));
    }

    if (isRequestIncludePayload()) {
      String payload = getRequestMessagePayload(request);
      if (payload != null) {
        msg.append(", payload=").append(payload);
      }
    }

    msg.append(REQUEST_DEFAULT_MESSAGE_SUFFIX);
    return msg.toString();
  }

  public String createResponseMessage(String id, Long elapsedMillis, HttpServletResponse response) {
    StringBuilder msg = new StringBuilder();
    msg.append(String.format(RESPONSE_DEFAULT_MESSAGE_PREFIX, id));
    msg.append(response.getStatus());

    if (isResponseIncludeHeaders()) {
      HttpHeaders headers;
      try (ServletServerHttpResponse servletServerHttpResponse =
          new ServletServerHttpResponse(response)) {
        headers = servletServerHttpResponse.getHeaders();
      }
      if (getResponseHeaderPredicate() != null) {
        Collection<String> names = response.getHeaderNames();
        names.forEach(
            header -> {
              if (!getResponseHeaderPredicate().test(header)) {
                headers.set(header, "masked");
              }
            });
      }
      msg.append(", headers=").append(ParamsUtils.formatHeaders(headers));
    }

    if (isResponseIncludePayload()) {
      String payload = getResponseMessagePayload(response);
      if (payload != null) {
        msg.append(", payload=").append(payload);
      }
    }

    msg.append(String.format(RESPONSE_DEFAULT_MESSAGE_SUFFIX, elapsedMillis));
    return msg.toString();
  }


  protected String getRequestMessagePayload(HttpServletRequest request) {
    RepeatableContentCachingRequestWrapper wrapper =
        WebUtils.getNativeRequest(request, RepeatableContentCachingRequestWrapper.class);
    if (wrapper != null) {
      try {
        byte[] buf = StreamUtils.copyToByteArray(wrapper.getInputStream());
        if (buf.length > 0) {
          return safelyEncodePayload(buf, wrapper);
        } else {
          return null;
        }
      } catch (IOException e) {
        log.info("Error 'unknown-read'", e);
        return "[unknown-read]";
      }
    }
    return null;
  }

  private String safelyEncodePayload(byte[] buf, RepeatableContentCachingRequestWrapper wrapper) {
    int length = Math.min(buf.length, getRequestMaxPayloadLength());
    try {
      return new String(buf, 0, length, wrapper.getCharacterEncoding());
    } catch (UnsupportedEncodingException e) {
      log.info("Error 'unknown-encoding'", e);
      return "[unknown-encoding]";
    }
  }


  protected String getResponseMessagePayload(HttpServletResponse response) {
    ContentCachingResponseWrapper wrapper =
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    if (wrapper != null) {
      byte[] buf = wrapper.getContentAsByteArray();
      if (buf.length > 0) {
        int length = Math.min(buf.length, getResponseMaxPayloadLength());
        try {
          return new String(buf, 0, length, wrapper.getCharacterEncoding());
        } catch (UnsupportedEncodingException ex) {
          log.info("Error 'unknown'", ex);
          return "[unknown]";
        }
      }
    }
    return null;
  }

  protected boolean shouldRequestLog(HttpServletRequest request) {
    return true;
  }

  protected boolean shouldResponseLog(HttpServletResponse response) {
    return true;
  }

  protected abstract void request(HttpServletRequest request, String message);

  protected abstract void response(HttpServletResponse response, String message);

  protected abstract void requestDB(
      String id, HttpServletRequest request, String callerid);

  protected abstract void responseDB(
      String id,
      Long elapsed,
      HttpServletResponse response,
      String callerid,
      String iuv,
      String paFiscalCode);
}
