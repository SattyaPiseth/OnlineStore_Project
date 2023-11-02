package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(@NotBlank
                            String email,
                            @NotBlank
                            String nickName) {
}
