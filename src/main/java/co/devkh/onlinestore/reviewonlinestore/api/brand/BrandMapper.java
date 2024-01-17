package co.devkh.onlinestore.reviewonlinestore.api.brand;

import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.UpdateBrandToCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand fromBrandDto(BrandDto brandDto);
    BrandDto toBrandDto(Brand brand);
    List<BrandDto> toBrandDtoList(List<Brand> brands);
   // @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void fromBrandDto(@MappingTarget Brand brand, BrandDto updateBrandDto);
}
