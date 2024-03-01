package co.devkh.onlinestore.reviewonlinestore.api.brand.service;

import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.NewBrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.UpdateBrandToCategoryDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;


public interface BrandService {
/**
     * This method is used to create new brand
     * @param newBrandDto is the request data from client
     */
    void createNew(NewBrandDto newBrandDto);

    /**
     * This method is used to retrieve all brand from database
     * @param request is the request data from client
     * @return StructureRS
     */
    StructureRS findAll(BaseListingRQ request);

    /**
     * This method is used to retrieve brand from database by uuid
     * @param uuid is identifier of brand
     * @param request is the request data from client
     * @return StructureRS
     */
    StructureRS findByUuid(String uuid, BaseListingRQ request);

    /**
     * This method is used to update brand by uuid
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
