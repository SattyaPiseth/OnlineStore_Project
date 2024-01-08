package co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record VerifyTokenDto(@NotBlank
                             @Email
                             String email,
                             @NotBlank
                             String verifiedToken) {
}
