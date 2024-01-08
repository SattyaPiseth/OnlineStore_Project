package co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto;

import lombok.Builder;

@Builder
public record AuthDto(String accessToken,
                      String refreshToken,
                      String type) {
}
