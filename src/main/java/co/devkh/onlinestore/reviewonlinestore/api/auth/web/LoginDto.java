package co.devkh.onlinestore.reviewonlinestore.api.auth.web;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDto(@NotBlank
                       String username,
                       @NotBlank
                       String password) {
}
