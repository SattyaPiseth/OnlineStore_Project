package co.devkh.onlinestore.reviewonlinestore.exception.category;

import co.devkh.onlinestore.reviewonlinestore.base.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class NoSuchElementException {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(java.util.NoSuchElementException.class)
    public Map<String,Object> handleNoSuchElementException(java.util.NoSuchElementException exception){

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("notify","failed to find category by name!, please check your input uri.");
        errorMap.put("code",7000);
        errorMap.put("status",false);
        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("error", "NoSuchElementException occurred");
        errorMap.put("message", exception.getMessage()); // Optional: include exception message
        // You can add more details or handle the exception as needed

        // Log the exception or perform other operations

        return errorMap;
    }
}
