package co.devkh.onlinestore.reviewonlinestore.api.product.business;

import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CategoryDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;

public interface CategoryService {
    /**
     * This method is used to create a new category
     * resource into database
     * @param categoryDto is the request data from client
     */
    void createNew(CategoryDto categoryDto);

/** This method is used to find category by id
 *
 * @param id of category
 * @return StructureRS
 */
    StructureRS findById(Integer id, BaseListingRQ request);


    /**
     * This method is used to delete category from database by name (not recommended)
     * @param name is identifier name of category (permanently delete)
     *
     */
    void deleteByName(String name);

    /**
     * This method is used to delete category from database by id (not recommended)
     * @param Id is identifier of category (permanently delete)
     */
    void deleteById(Integer Id);

    /**
     * This method is used to update category from database by id
     * @param id is identifier of category
     * @param updateCategoryDto is the request data from client
     */
    void updateById(Integer id,CategoryDto updateCategoryDto);

    /**
     * This method is used to get all category from database
     * @param request is the request data from client
     *
     * @return StructureRS
     */
    StructureRS getAll(BaseListingRQ request);
}
