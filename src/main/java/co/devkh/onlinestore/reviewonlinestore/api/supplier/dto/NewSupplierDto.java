package co.devkh.onlinestore.reviewonlinestore.api.supplier.dto;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Sattya
 * create at 1/28/2024 11:02 PM
 */
public record NewSupplierDto(
        @NotBlank(message = "Company name must not be blank")
        String companyName,
        @NotBlank(message = "Contact name must not be blank")
        String contactName,
        String contactTitle,
        @Email(message = "Contact email must be valid")
        @NotBlank(message = "Contact email must not be blank")
        String contactEmail,
        @NotBlank(message = "Address must not be blank")
        String address,
        @NotBlank(message = "City must not be blank")
        String city,
        @NotBlank(message = "Country must not be blank")
        String country,
        @Size(min = 10,max = 15,message = "Phone must be between 10 and 15 characters")
        @NotBlank(message = "Phone must not be blank")
        String phone,
        @Size(min = 1,message = "Categories must not be empty")
        @NotNull(message = "Categories must not be null")
        List<@Positive Integer> categoriesIds
) {
}
