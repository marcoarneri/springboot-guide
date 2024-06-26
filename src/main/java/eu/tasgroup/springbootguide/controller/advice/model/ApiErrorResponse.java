package eu.tasgroup.springbootguide.controller.advice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@JsonPropertyOrder({
  "errorId",
  "timestamp",
  "httpStatusCode",
  "httpStatusDescription",
  "appErrorCode",
  "message",
  "errors"
})
public class ApiErrorResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(
          example = "50905466-1881-457b-b42f-fb7b2bfb1610",
          description = "This field exist only if status=500")
  @Setter
  private String errorId;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", timezone = "UTC")
  @Schema(example = "2023-10-05T11:22:57.494928Z", requiredMode = Schema.RequiredMode.REQUIRED)
  private Instant timestamp;

  @Schema(example = "500", requiredMode = Schema.RequiredMode.REQUIRED)
  private int httpStatusCode;

  @Schema(example = "Internal Server Error", requiredMode = Schema.RequiredMode.REQUIRED)
  private String httpStatusDescription;

  @Schema(example = "FDR-500", requiredMode = Schema.RequiredMode.REQUIRED)
  private String appErrorCode;

  @Schema(
          example = "An unexpected error has occurred. Please contact support.",
          requiredMode = Schema.RequiredMode.REQUIRED)
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ErrorMessage> errors;

  @Builder
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonPropertyOrder({"path", "message"})
  public static class ErrorMessage {

    @Schema(example = "object.field")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;

    @Schema(
            example = "An unexpected error has occurred. Please contact support.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;
  }
}
