package co.devkh.onlinestore.reviewonlinestore.api.product;

import co.devkh.onlinestore.reviewonlinestore.api.product.web.CategoryDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromCategoryDto(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);
    List<CategoryDto> toCategoryDtoList(List<Category> categories);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCategoryDto(@MappingTarget Category category,CategoryDto updateCategoryDto);
}
