package co.devkh.onlinestore.reviewonlinestore.api.user.web.authority_dto;


import jakarta.validation.constraints.NotBlank;

public record NewAuthorityDto(
        @NotBlank(message = "Name is required")
        String name
) {
}
