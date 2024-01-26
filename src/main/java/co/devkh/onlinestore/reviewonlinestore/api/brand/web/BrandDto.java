package co.devkh.onlinestore.reviewonlinestore.api.brand.web;

import jakarta.validation.constraints.NotBlank;


public record BrandDto(
        @NotBlank(message = "brand name is required")
        String brandName
) {
}
