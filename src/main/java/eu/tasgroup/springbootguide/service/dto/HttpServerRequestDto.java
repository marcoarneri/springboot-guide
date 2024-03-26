package eu.tasgroup.springbootguide.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HttpServerRequestDto {
  private String serverId;
  private String method;
  private String uriWithQueryString;

  private String clientInfo;
  private String headers;
  private String payload;

  private String iuv;
  private String paFiscalCode;
  private String callerId;
}
