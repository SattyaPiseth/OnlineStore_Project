package co.devkh.onlinestore.reviewonlinestore.api.brand.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BrandDto(
        @NotBlank(message = "brand name is required")
        String brandName

) {
}
