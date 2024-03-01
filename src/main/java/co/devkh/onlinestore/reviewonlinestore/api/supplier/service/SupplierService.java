package co.devkh.onlinestore.reviewonlinestore.api.supplier.service;

import co.devkh.onlinestore.reviewonlinestore.api.supplier.data.Supplier;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.dto.NewSupplierDto;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.dto.updateSupplierDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;

/**
 * @author Sattya
 * create at 1/28/2024 10:57 PM
 */
public interface SupplierService {
    /**
     * This method is used to create new supplier to database
     * @param supplier is the request data from client
     */
    void createNew(Supplier supplier, NewSupplierDto newSupplierDto);

    /**
     * This method is used to retrieve all supplier from database
     * @param request is the request data from client
     * @return StructureRS is the response data to client
     */
    StructureRS findAll(BaseListingRQ request);

    /**
     * This method is used to retrieve supplier from database by uuid
     * @param uuid is identifier of supplier
     * @param request is the request data from client
     * @return StructureRS is the response data to client
     */
    StructureRS findByUuid(String uuid, BaseListingRQ request);

    /**
     * This method is used to update supplier by uuid
     * @param uuid is identifier of supplier
     * @param updateSupplierDto is the request data from client
     */
    void updateByUuid(String uuid, updateSupplierDto updateSupplierDto);

    /**
     * This method is used to delete supplier by uuid
     * @param uuid is identifier of supplier
     */
    void deleteByUuid(String uuid);
}
