package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record NewRoleDto(
        @NotBlank(message = "Name is required")
                @Positive(message = "Id must be positive")
        Integer id,
        @NotBlank(message = "Name is required")
        String name
) {
}
