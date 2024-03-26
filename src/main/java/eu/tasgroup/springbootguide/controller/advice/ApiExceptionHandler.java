package eu.tasgroup.springbootguide.controller.advice;


import eu.tasgroup.springbootguide.controller.advice.model.ApiErrorResponse;
import eu.tasgroup.springbootguide.exception.AppErrorCodeMessageEnum;
import eu.tasgroup.springbootguide.exception.AppException;
import eu.tasgroup.springbootguide.util.AppErrorUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final AppErrorUtil appErrorUtil;

  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "500",
                          description = "Internal Server Error",
                          content = {
                                  @Content(
                                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                                          schema = @Schema(implementation = ApiErrorResponse.class),
                                          examples = {
                                                  @ExampleObject(
                                                          "{\n"
                                                                  + "    \"errorId\": \"68ce8c6a-6d53-486c-97fe-79430d24fb7d\",\n"
                                                                  + "    \"timestamp\": \"2023-10-09T08:01:39.421224Z\",\n"
                                                                  + "    \"httpStatusCode\": 500,\n"
                                                                  + "    \"httpStatusDescription\": \"Internal Server Error\",\n"
                                                                  + "    \"appErrorCode\": \"DEMO-0500\",\n"
                                                                  + "    \"message\": \"An unexpected error has occurred. Please contact"
                                                                  + " support\"\n"
                                                                  + "}")
                                          })
                          }),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Bad Request",
                          content = {
                                  @Content(
                                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                                          schema = @Schema(implementation = ApiErrorResponse.class),
                                          examples = {
                                                  @ExampleObject(
                                                          "{\n"
                                                                  + "    \"timestamp\": \"2023-10-09T07:53:14.077792Z\",\n"
                                                                  + "    \"httpStatusCode\": 400,\n"
                                                                  + "    \"httpStatusDescription\": \"Bad Request\",\n"
                                                                  + "    \"appErrorCode\": \"DEMO-0400\",\n"
                                                                  + "    \"message\": \"Bad request\",\n"
                                                                  + "    \"errors\": [\n"
                                                                  + "        {\n"
                                                                  + "            \"message\": \"Field error in ...\"\n"
                                                                  + "        }\n"
                                                                  + "    ]\n"
                                                                  + "}")
                                          })
                          }),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Not found",
                          content = {
                                  @Content(
                                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                                          schema = @Schema(implementation = ApiErrorResponse.class),
                                          examples = {
                                                  @ExampleObject(
                                                          "{\n"
                                                                  + "    \"timestamp\": \"2023-10-09T07:53:43.367312Z\",\n"
                                                                  + "    \"httpStatusCode\": 404,\n"
                                                                  + "    \"httpStatusDescription\": \"Not Found\",\n"
                                                                  + "    \"appErrorCode\": \"DEMO-0404\",\n"
                                                                  + "    \"message\": \"Request POST /api/v1/..... not found\"\n"
                                                                  + "}")
                                          })
                          })
          })
  @ExceptionHandler(AppException.class)
  public ResponseEntity<ApiErrorResponse> handleAppException(AppException appEx) {
    Pair<HttpStatus, ApiErrorResponse> httpStatusApiErrorResponsePair =
            appErrorUtil.buildApiErrorResponse(appEx, null, null);
    return ResponseEntity.status(httpStatusApiErrorResponsePair.getLeft())
            .body(httpStatusApiErrorResponsePair.getRight());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    List<ApiErrorResponse.ErrorMessage> errorMessages =
            ex.getAllErrors().stream()
                    .map(oe -> ApiErrorResponse.ErrorMessage.builder().message(oe.getDefaultMessage()).build())
                    .collect(Collectors.toList());
    AppException appEx = new AppException(ex, AppErrorCodeMessageEnum.BAD_REQUEST);
    Pair<HttpStatus, ApiErrorResponse> httpStatusApiErrorResponsePair =
            appErrorUtil.buildApiErrorResponse(appEx, null, errorMessages);
    return ResponseEntity.status(httpStatusApiErrorResponsePair.getLeft())
            .body(httpStatusApiErrorResponsePair.getRight());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleExceptiond(Exception ex) {
    log.error(String.format("ExceptionHandler: %s", ex.getMessage()), ex);

    AppException appEx = new AppException(ex, AppErrorCodeMessageEnum.ERROR);
    // errorId viene usato solo per i casi di eccezioni non gestite
    String errorId = UUID.randomUUID().toString();
    Pair<HttpStatus, ApiErrorResponse> httpStatusApiErrorResponsePair =
            appErrorUtil.buildApiErrorResponse(appEx, errorId, null);
    return ResponseEntity.status(httpStatusApiErrorResponsePair.getLeft())
            .body(httpStatusApiErrorResponsePair.getRight());
  }


    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<ApiErrorResponse.ErrorMessage> errorMessages =
                ex.getConstraintViolations().stream()
                        .map(oe -> ApiErrorResponse.ErrorMessage.builder().message(oe.toString()).build())
                        .collect(Collectors.toList());
        AppException appEx = new AppException(ex, AppErrorCodeMessageEnum.BAD_REQUEST);
        Pair<HttpStatus, ApiErrorResponse> httpStatusApiErrorResponsePair =
                appErrorUtil.buildApiErrorResponse(appEx, null, errorMessages);
        return ResponseEntity.status(httpStatusApiErrorResponsePair.getLeft())
                .body(httpStatusApiErrorResponsePair.getRight());
    }
}
