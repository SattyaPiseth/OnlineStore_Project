package co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyDto(@NotBlank
                        @Email
                        String email,
                        @NotBlank
                        String verifiedCode) {
}
