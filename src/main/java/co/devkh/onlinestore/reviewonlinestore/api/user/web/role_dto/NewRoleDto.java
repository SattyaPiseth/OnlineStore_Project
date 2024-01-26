package co.devkh.onlinestore.reviewonlinestore.api.user.web.role_dto;

import jakarta.validation.constraints.NotBlank;

public record NewRoleDto(
        @NotBlank(message = "Name is required")
        String name) {
}
