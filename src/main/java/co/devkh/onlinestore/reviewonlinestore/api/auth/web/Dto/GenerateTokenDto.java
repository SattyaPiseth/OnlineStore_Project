package co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto;

import lombok.Builder;

import java.time.Duration;
import java.time.Instant;
@Builder
public record GenerateTokenDto(String auth,
                               Instant expiration,
                               String scope,
                               Duration duration,
                               Integer checkDurationValue,
                               String previousToken) {
}
