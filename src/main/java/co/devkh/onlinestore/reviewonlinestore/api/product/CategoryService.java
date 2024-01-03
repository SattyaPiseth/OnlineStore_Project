package co.devkh.onlinestore.reviewonlinestore.api.product;

import co.devkh.onlinestore.reviewonlinestore.api.product.web.CategoryDto;

import java.util.List;

public interface CategoryService {
    /**
     * This method is used to create a new category
     * resource into database
     * @param categoryDto is the request data from client
     */
    void createNew(CategoryDto categoryDto);

    /**
     * This method is used to retrieve resource category from database
     * @return List<CategoryDto>
     */
    List<CategoryDto> findAll();

    /**
     * This method is used to retrieve resource category by name
     * from database
     * @param name of category
     * @return CategoryDto
     */
    CategoryDto findByName(String name);

    /**
     * This method is used to delete category from database by uuid
     * @param Id is identifier of category
     *
     * @return CategoryDto
     */
    CategoryDto findById(Integer Id);


    /**
     * This method is used to delete category from database by uuid
     * @param name is identifier name of category
     */
    void deleteByName(String name);

    /**
     * This method is used to delete category from database by uuid
     * @param Id is identifier of category
     */
    void deleteById(Integer Id);

    /**
     * This method is used to update category from database by uuid
     * @param id is identifier of category
     * @param updateCategoryDto is the request data from client
     */
    void updateById(Integer id,CategoryDto updateCategoryDto);
}
