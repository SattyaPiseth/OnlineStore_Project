package co.devkh.onlinestore.reviewonlinestore.api.brand.service;

import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.NewBrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.UpdateBrandToCategoryDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;


public interface BrandService {
    /**
     * This method is used to create a new brand
     * resource into database
     * @param newBrandDto is the request data from client
     */
    void createNew(NewBrandDto newBrandDto);

    /**
     * This method is used to retrieve resource brand from database
     * @return StructureRS
     */
    StructureRS findAll(BaseListingRQ request);

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
     * @param brandUuid is identifier of brand
     * @param categoryId is identifier of category
     */

    void assignBrandToCategory(String brandUuid, Integer categoryId);

    /**
     * This method is used to unassign brand from category
     * @param brandUuid is identifier of brand
     * @param categoryId is identifier of category
     */

    void unAssignBrandFromCategory(String brandUuid, Integer categoryId);

    /**
     * This method is used to update brand to category
     * @param brandUuid is identifier of brand
     * @param categoryId is identifier of category
     * @param updateBrandToCategoryDto is the request data from client
     */
    void updateBrandToCategory(String brandUuid, Integer categoryId, UpdateBrandToCategoryDto updateBrandToCategoryDto);
}
