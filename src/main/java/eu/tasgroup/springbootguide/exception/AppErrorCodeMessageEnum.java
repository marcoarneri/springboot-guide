package eu.tasgroup.springbootguide.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum AppErrorCodeMessageEnum {
    ERROR("0500", "system.error", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("0400", "bad.request", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_FOUND("1102", "payment.not.found", HttpStatus.NOT_FOUND),
    BAD_REQUEST_PA_FISCAL_CODE_NOT_BLANK("1408", "paFiscalCode.notBlank", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_PA_FISCAL_CODE_LENGTH("1409", "paFiscalCode.length6", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_NOTICE_NUMBER_NOT_BLANK("1410", "noticeNumber.notBlank", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_NOTICE_NUMBER_LENGTH("1411", "noticeNumber.length", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_PAYMENT_TOKEN_NOT_BLANK("1412", "paymentToken.notBlank", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_PAYMENT_TOKEN_LENGTH("1413", "paymentToken.length", HttpStatus.BAD_REQUEST);





    private final String errorCode;
    private final String errorMessageKey;
    private final HttpStatus httpStatus;

    AppErrorCodeMessageEnum(String errorCode, String errorMessageKey, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessageKey = errorMessageKey;
        this.httpStatus = httpStatus;
    }
}
