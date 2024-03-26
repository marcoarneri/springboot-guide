package eu.tasgroup.springbootguide.repository.model;

import lombok.Getter;

@Getter
public enum AccessLogTypeEnum {
  HTTP_SERVER_REQUEST,
  HTTP_SERVER_RESPONSE,
  HTTP_CLIENT_REQUEST,
  HTTP_CLIENT_RESPONSE,
  HTTP_CLIENT_RESPONSE_ERROR;
}
