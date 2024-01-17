package co.devkh.onlinestore.reviewonlinestore.api.brand.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateBrandToCategoryDto(
        @NotNull(message = "At least one brand ID must be provided.")
        @Size(min = 1, message = "At least one brand ID must be provided.")
        Set<Integer> brandIds
) {
}
