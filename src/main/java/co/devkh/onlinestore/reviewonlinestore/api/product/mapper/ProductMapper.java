package co.devkh.onlinestore.reviewonlinestore.api.product.mapper;

import co.devkh.onlinestore.reviewonlinestore.api.product.data.Product;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CreateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.ProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.UpdateProductDto;
import org.mapstruct.*;



@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categoryId",target = "category.id")
    @Mapping(source = "brandId",target = "brand.id")
    @Mapping(source = "supplierId",target = "supplier.id")
    Product fromCreateProductDto(CreateProductDto createProductDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateProductDto(@MappingTarget Product product, UpdateProductDto updateProductDto);
    @Mapping(source = "category.name",target = "categoryName")
    @Mapping(source = "brand.brandName",target = "brandName")
    @Mapping(source = "supplier.companyName",target = "supplierName")
    ProductDto toProductDto(Product product);

}
