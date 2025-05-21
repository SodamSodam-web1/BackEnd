package goormton.backend.sodamsodam.global.error;

import goormton.backend.sodamsodam.global.payload.ErrorCode;
import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class InvalidParameterException extends DefaultExeption{

    private Errors errors;

    public InvalidParameterException(Errors errors) {
        super(ErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }

    public List<FieldError> getFieldErrors() {
        return errors.getFieldErrors();
    }
}
