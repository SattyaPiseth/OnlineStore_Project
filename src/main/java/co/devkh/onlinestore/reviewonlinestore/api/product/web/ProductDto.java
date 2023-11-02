package co.devkh.onlinestore.reviewonlinestore.api.product.web;

public record ProductDto(String uuid,
                         String code,
                         String name,
                         String description,
                         String image,
                         String categoryName) {
}
