package co.devkh.onlinestore.reviewonlinestore.api.product;

import co.devkh.onlinestore.reviewonlinestore.api.product.web.CreateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.ProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.UpdateProductDto;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId",target = "category.id")
    Product fromCreateProductDto(CreateProductDto createProductDto);
//    @Mapping(source = "categoryId",target = "category.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateProductDto(@MappingTarget Product product, UpdateProductDto updateProductDto);
    @Mapping(source = "category.name",target = "categoryName")
    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);
}
