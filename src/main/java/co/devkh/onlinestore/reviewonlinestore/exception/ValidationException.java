package co.devkh.onlinestore.reviewonlinestore.exception;

import co.devkh.onlinestore.reviewonlinestore.base.error.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleValidationException(MethodArgumentNotValidException exception){

        Map<String,Object> errorDto = new HashMap<>();
        List<FieldError> errors = new ArrayList<>();

        exception.getFieldErrors().forEach(fieldError -> {
            errors.add(new FieldError(fieldError.getField(),
                    fieldError.getDefaultMessage()));
        });
        errorDto.put("message","Validation failed, please check your input.");
        errorDto.put("code",7000);
        errorDto.put("status",false);
        errorDto.put("timestamp", LocalDateTime.now());
        errorDto.put("errors",errors);
        return errorDto;
    }

}
