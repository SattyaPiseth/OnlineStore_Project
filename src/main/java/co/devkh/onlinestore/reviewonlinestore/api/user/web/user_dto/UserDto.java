package co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto;

import lombok.Builder;

@Builder
public record UserDto(
                      Long id,
                      String uuid,
                      String username,
                      String email,
                      String nickName) {
}
