package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import lombok.Builder;

@Builder
public record UserDto(
                      Long id,
                      String uuid,
                      String username,
                      String email,
                      String nickName) {
}
