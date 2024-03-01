package co.devkh.onlinestore.reviewonlinestore.api.product.mapper;

import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromCategoryDto(CategoryDto categoryDto);
//    CategoryDto toCategoryDto(Category category);

//    List<CategoryDto> toCategoryDtoList(List<Category> categories);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCategoryDto(@MappingTarget Category category,CategoryDto updateCategoryDto);
}


