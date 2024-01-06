package co.devkh.onlinestore.reviewonlinestore.api.auth.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDto(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
