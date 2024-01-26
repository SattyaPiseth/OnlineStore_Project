package co.devkh.onlinestore.reviewonlinestore.api.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(@NotBlank
                          String name,
                          @NotBlank
                          @Size(min = 5)
                          String description) {
}
