package co.devkh.onlinestore.reviewonlinestore.base.error;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record BaseError<T> (String message,
                        Integer code,
                        Boolean status,
                        LocalDateTime timestamp,
                        T errors) {
}
