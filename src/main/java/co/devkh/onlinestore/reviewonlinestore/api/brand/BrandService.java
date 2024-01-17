package co.devkh.onlinestore.reviewonlinestore.api.brand;

import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.UpdateBrandToCategoryDto;

import java.util.List;

public interface BrandService {
    /**
     * This method is used to create a new brand
     * resource into database
     * @param brandDto is the request data from client
     */
    void createNew(BrandDto brandDto);

    /**
     * This method is used to retrieve resource brand from database
     * @return List<BrandDto>
     */
    List<BrandDto> findAll();

    /**
     * This method is used to retrieve resource brand by name
     * from database
     * @param name of brand
     * @return BrandDto
     */
    BrandDto findByName(String name);

    /**
     * This method is used to retrieve resource brand by id
     * @param uuid of brand
     * @return BrandDto
     */
    BrandDto findByUuid(String uuid);

    /**
     * This method is used to update brand from database by id
     * @param uuid is identifier of brand
     * @param updateBrandDto is the request data from client
     */
    void updateByUuid(String uuid,BrandDto updateBrandDto);

    /**
     * This method is used to assign brand to category
     * @param brandId is identifier of brand
     * @param categoryId is identifier of category
     */

    void assignBrandToCategory(Integer brandId, Integer categoryId);

    /**
     * This method is used to unassign brand from category
     * @param brandId is identifier of brand
     * @param categoryId is identifier of category
     */

    void unAssignBrandFromCategory(Integer brandId, Integer categoryId);

    /**
     * This method is used to update brand to category
     * @param brandId is identifier of brand
     * @param categoryId is identifier of category
     * @param updateBrandToCategoryDto is the request data from client
     */
    void updateBrandToCategory(Integer brandId, Integer categoryId, UpdateBrandToCategoryDto updateBrandToCategoryDto);
}
