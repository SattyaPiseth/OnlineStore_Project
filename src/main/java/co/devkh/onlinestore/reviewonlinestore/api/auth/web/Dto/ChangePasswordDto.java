package co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDto(
        @NotBlank
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Old password is required")
        String oldPassword,
        @NotBlank(message = "New password is required")
        String newPassword
) {
}
