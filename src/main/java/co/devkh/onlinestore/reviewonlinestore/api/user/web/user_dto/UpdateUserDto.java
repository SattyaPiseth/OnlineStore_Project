package co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(@NotBlank
                            String email,
                            @NotBlank
                            String nickName) {
}
