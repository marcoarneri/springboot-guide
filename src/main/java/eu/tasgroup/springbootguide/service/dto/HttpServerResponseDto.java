package eu.tasgroup.springbootguide.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HttpServerResponseDto {
  private String serverId;
  private int status;
  private String headers;
  private String payload;
  private Long elapsed;

  private String iuv;
  private String paFiscalCode;
  private String callerId;
}
