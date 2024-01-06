package co.devkh.onlinestore.reviewonlinestore.api.auth.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ChangePasswordDto(
        @NotBlank(message = "Old password is required")
        String oldPassword,
        @NotBlank(message = "New password is required")
        String newPassword
) {
}
