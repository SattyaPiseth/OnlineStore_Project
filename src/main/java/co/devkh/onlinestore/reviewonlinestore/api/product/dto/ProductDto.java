package co.devkh.onlinestore.reviewonlinestore.api.product.dto;

public record ProductDto(String uuid,
                         String code,
                         String name,
                         Double unitPrice,
                         String description,
                         String image,
                         String categoryName,
                         String brandName,
                         String supplierName) {
}
