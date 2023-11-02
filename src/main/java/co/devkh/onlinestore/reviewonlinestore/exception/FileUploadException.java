package co.devkh.onlinestore.reviewonlinestore.exception;

import co.devkh.onlinestore.reviewonlinestore.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.naming.SizeLimitExceededException;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class FileUploadException {
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseError<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception){

        return BaseError.builder()
                .message("Something went wrong!")
                .code(7020)
                .status(false)
                .timestamp(LocalDateTime.now())
                .errors(exception.getMessage())
                .build();
    }
}
