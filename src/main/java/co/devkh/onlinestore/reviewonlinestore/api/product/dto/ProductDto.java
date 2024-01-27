package co.devkh.onlinestore.reviewonlinestore.api.product.dto;

/**
 * @author Sattya
 * create at 1/27/2024 11:18 PM
 */
public record ProductDto(
        String uuid,
        String code,
        String name,
        String description,
        String image,
        String categoryName,
        String brandName,
        String supplierName
) {
}
