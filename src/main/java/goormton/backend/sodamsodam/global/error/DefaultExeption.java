package goormton.backend.sodamsodam.global.error;

import goormton.backend.web1team.global.payload.ErrorCode;
import lombok.Getter;

@Getter
public class DefaultExeption extends RuntimeException {

    private ErrorCode errorCode;

    public DefaultExeption(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public DefaultExeption(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
