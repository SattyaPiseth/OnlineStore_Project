package co.devkh.onlinestore.reviewonlinestore.api.brand.web;

import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CategoryDto;

import java.util.Set;

public record PagingBrandDto(
        String brandName,
        Set<CategoryDto> categories
) {
}
