package co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterDto(@NotBlank
                          String username,
                          @NotBlank
                          @Email
                          String email,
                          @NotBlank
                          String password,
                          @NotBlank
                          @Size(min = 4)
                          String nickName,
                          @Size(min = 1)
                          Set<@Positive Integer> roleIds) {
}
