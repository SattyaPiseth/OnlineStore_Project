package co.devkh.onlinestore.reviewonlinestore.api.product.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateProductDto(//@NotBlank
                               @Size(min = 5, max = 255)
                               String name,
                               //@NotBlank
                               @Size(min = 5,message = "Description must be greater than 5.")
                               String description,
                               @Positive(message = "Unit price must be greater than 0.")
                               Double unitPrice,
                               //@NotNull
                               @Positive(message = "Category ID must be greater than 0.")
                               Integer categoryId,
                               @Positive(message = "Brand ID must be greater than 0.")
                               Integer brandId,
                               @Positive(message = "Supplier ID must be greater than 0.")
                               Integer supplierId) {
}
