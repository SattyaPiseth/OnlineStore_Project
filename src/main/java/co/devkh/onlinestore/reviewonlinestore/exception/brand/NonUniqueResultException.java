package co.devkh.onlinestore.reviewonlinestore.exception.brand;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class NonUniqueResultException {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(jakarta.persistence.NonUniqueResultException.class)
    public Map<String,Object> handleNonUniqueResultException(jakarta.persistence.NonUniqueResultException exception){

        Map<String,Object> errorMap = new HashMap<>();
        errorMap.put("notify","failed to retrieve data because query did not return a unique result of brand name");
        errorMap.put("code",7000);
        errorMap.put("status",false);
        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("error","NonUniqueResultException occurred");
        errorMap.put("message",exception.getMessage());

        return errorMap;
    }
}