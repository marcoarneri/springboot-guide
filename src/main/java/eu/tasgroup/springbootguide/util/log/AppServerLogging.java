package eu.tasgroup.springbootguide.util.log;

import eu.tasgroup.springbootguide.service.AccessLogService;
import eu.tasgroup.springbootguide.service.dto.HttpServerRequestDto;
import eu.tasgroup.springbootguide.service.dto.HttpServerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Slf4j
public class AppServerLogging extends AbstractAppServerLogging {

    private final AccessLogService accessLogService;

    @Override
    protected boolean shouldRequestLog(jakarta.servlet.http.HttpServletRequest request) {
        return log.isDebugEnabled();
    }

    @Override
    protected boolean shouldResponseLog(jakarta.servlet.http.HttpServletResponse response) {
        return log.isDebugEnabled();
    }


    @Override
    protected void request(jakarta.servlet.http.HttpServletRequest request, String message) {
        log.debug(message);
    }

    @Override
    protected void response(jakarta.servlet.http.HttpServletResponse response, String message) {
        log.debug(message);
    }

    @SneakyThrows
    @Override
    protected void requestDB(String id, jakarta.servlet.http.HttpServletRequest request, String callerid) {
        HttpServerRequestDto req = getHttpServerRequest(id, request, callerid);
        accessLogService.save(req);
    }

    @SneakyThrows
    @Override
    protected void responseDB(String id, Long elapsed, jakarta.servlet.http.HttpServletResponse response, String callerid, String iuv, String paFiscalCode) {
        HttpServerResponseDto res =
                getHttpServerResponse(id, elapsed, response, callerid, iuv, paFiscalCode);
        accessLogService.save(res);
    }

  private HttpServerRequestDto getHttpServerRequest(
      String id, HttpServletRequest request, String callerId)
      throws UnsupportedEncodingException {
    HttpServerRequestDto httpServerRequest = new HttpServerRequestDto();
    httpServerRequest.setCallerId(callerId);
    httpServerRequest.setServerId(id);
    httpServerRequest.setMethod(request.getMethod());

    StringBuilder uriWithQueryString = new StringBuilder(request.getRequestURI());
    String queryString = request.getQueryString();
    if (queryString != null) {
      uriWithQueryString.append('?').append(queryString);
    }
    httpServerRequest.setUriWithQueryString(uriWithQueryString.toString());

    StringBuilder clientInfo = new StringBuilder();
    String client = request.getRemoteAddr();
    if (StringUtils.hasLength(client)) {
      clientInfo.append("client=").append(client);
    } else {
      clientInfo.append("client=[na]");
    }
    HttpSession session = request.getSession(false);
    if (session != null) {
      clientInfo.append(", session=").append(session.getId());
    } else {
      clientInfo.append(", session=[na]");
    }
    String user = request.getRemoteUser();
    if (user != null) {
      clientInfo.append(", user=").append(user);
    } else {
      clientInfo.append(", user=[na]");
    }

    httpServerRequest.setClientInfo(clientInfo.toString());

    HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
    httpServerRequest.setHeaders(headers.toString());

    RepeatableContentCachingRequestWrapper wrapper =
        WebUtils.getNativeRequest(request, RepeatableContentCachingRequestWrapper.class);
    String payload = null;
    if (wrapper != null && wrapper.getContentAsByteArray().length > 0) {
      payload = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
    }
    httpServerRequest.setPayload(payload);
    return httpServerRequest;
  }

  private HttpServerResponseDto getHttpServerResponse(
      String id,
      Long elapsedMillis,
      HttpServletResponse response,
      String callerId,
      String iuv,
      String paFiscalCode)
      throws UnsupportedEncodingException {
    HttpServerResponseDto httpServerResponse = new HttpServerResponseDto();
    httpServerResponse.setIuv(iuv);
    httpServerResponse.setCallerId(callerId);
    httpServerResponse.setPaFiscalCode(paFiscalCode);
    httpServerResponse.setServerId(id);
    httpServerResponse.setElapsed(elapsedMillis);
    httpServerResponse.setStatus(response.getStatus());

    HttpHeaders headers;
    try (ServletServerHttpResponse servletServerHttpResponse =
        new ServletServerHttpResponse(response)) {
      headers = servletServerHttpResponse.getHeaders();
    }
    httpServerResponse.setHeaders(headers.toString());

    ContentCachingResponseWrapper wrapper =
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    String payload = null;
    if (wrapper != null && wrapper.getContentAsByteArray().length > 0) {
      payload = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
    }
    httpServerResponse.setPayload(payload);
    return httpServerResponse;
  }

    @Override
    public void postHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
}
