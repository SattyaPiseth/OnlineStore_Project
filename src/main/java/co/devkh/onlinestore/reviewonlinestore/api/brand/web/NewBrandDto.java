package co.devkh.onlinestore.reviewonlinestore.api.brand.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record NewBrandDto(
        @NotBlank(message = "brand name is required")
        String brandName,

        @Size(min = 1)
        @NotNull(message = "category ids is required")
        Set<@Positive Integer> categoryIds
)
{

}
