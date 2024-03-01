package co.devkh.onlinestore.reviewonlinestore.api.supplier.mapper;

import co.devkh.onlinestore.reviewonlinestore.api.supplier.data.Supplier;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.dto.NewSupplierDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sattya
 * create at 1/28/2024 11:21 PM
 */
@Mapper(componentModel = "spring")
public interface SupplierMapper {
//    @Mapping(source = "categoriesIds",target = "categories.id")
//    Supplier fromNewSupplierDto(NewSupplierDto newSupplierDto);
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    Supplier toSupplier(NewSupplierDto newSupplierDto);

    NewSupplierDto toNewSupplierDto(Supplier supplier);
}
