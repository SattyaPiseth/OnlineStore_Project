package co.devkh.onlinestore.reviewonlinestore.api.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateProductDto(@NotBlank(message = "Name is required!")
                               @Size(min = 5,max = 255)
                               String name,
                               @NotBlank(message = "Description is required!")
                               @Size(min = 5,message = "Description must be greater than 5")
                               String description,
                               @NotBlank
                               String image,
                               @NotNull
                               @Positive
                               Double unitPrice,
                               @NotNull(message = "Category ID is required!")
                               @Positive(message = "Category ID must be greater than 0")
                               Integer categoryId,

                               @NotNull(message = "Brand ID is required!")
                               @Positive(message = "Brand ID must be greater than 0")
                               Integer brandId,

                               @NotNull(message = "Supplier ID is required!")
                               @Positive(message = "Supplier ID must be greater than 0")
                               Integer supplierId)
{
}
