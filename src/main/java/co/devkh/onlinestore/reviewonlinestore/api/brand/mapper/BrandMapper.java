package co.devkh.onlinestore.reviewonlinestore.api.brand.mapper;

import co.devkh.onlinestore.reviewonlinestore.api.brand.data.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.NewBrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.PagingBrandDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand fromNewBrandDto(NewBrandDto newBrandDto);

//    BrandDto toBrandDto(Brand brand);

   // @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void fromBrandDto(@MappingTarget Brand brand, BrandDto updateBrandDto);

//    PagingBrandDto toPagingBrandDto(Brand brand);

}
