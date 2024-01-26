package co.devkh.onlinestore.reviewonlinestore.api.product.business;

import co.devkh.onlinestore.reviewonlinestore.api.product.dto.UpdateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CreateProductDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;

public interface ProductService {
    /**
     * This method is used to create a new product
     * resource into database
     * @param createProductDto is the request data from client
     */
    void createNew(CreateProductDto createProductDto);

    /**
     * This method is used to update(Partially Update) product by uuid
     * @param uuid of product
     * @param updateProductDto is the request data from client for update
     */
    void updateByUuid(String uuid, UpdateProductDto updateProductDto);

    /**
     * This method is used to delete product from database by uuid
     * @param uuid of product
     */
    void deleteByUuid(String uuid);

    /**
     * This method is used to retrieve resource product from database
     *
     * @return List<ProductDto>
     */
//    Page<ProductDto> findAll(Map<String,String> params, int page, int size);

//    /**
//     * This method is used to retrieve resource product by uuid
//     * from database
//     * @param uuid of product
//     * @return ProductDto
//     */
//    ProductDto findByUuid(String uuid);

    StructureRS getAll(BaseListingRQ request);

}
