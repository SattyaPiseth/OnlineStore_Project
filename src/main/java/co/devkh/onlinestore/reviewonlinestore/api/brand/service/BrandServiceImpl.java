package co.devkh.onlinestore.reviewonlinestore.api.brand.service;

import co.devkh.onlinestore.reviewonlinestore.api.brand.mapper.BrandMapper;
import co.devkh.onlinestore.reviewonlinestore.api.brand.data.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.brand.data.BrandRepository;
import co.devkh.onlinestore.reviewonlinestore.api.brand.projection.BrandInfo;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.NewBrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.PagingBrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.UpdateBrandToCategoryDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.CategoryRepository;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl extends BaseService implements BrandService{
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createNew(NewBrandDto newBrandDto) {

        // Step 1: Check brand name existence
        if (brandRepository.existsByBrandName(newBrandDto.brandName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Brand Name = %s already exists in the database.", newBrandDto.brandName()));
        }

        // Step 2: Create new brand
        Brand brand = brandMapper.fromNewBrandDto(newBrandDto);
        brand.setBrandUuid(UUID.randomUUID().toString());

        // Step 3: Assign categories to brand
        Set<Category> categories = newBrandDto.categoryIds()
                .stream()
                .map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        if (categories.size() != newBrandDto.categoryIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more categories not found.");
        }

        brand.setCategories(categories);
        categories.forEach(category -> category.getBrands().add(brand));

        // Step 4: Save new brand and associated categories (transactional)
        brandRepository.save(brand);
    }

    @Override
    public StructureRS findAll(BaseListingRQ request) {
        Page<BrandInfo> brandInfoPage = brandRepository.findByBrandUuidStartsWithAndBrandNameStartsWith(request.getQuery(), request.getPageable(request.getSort(),request.getOrder()));
        return response(
                brandInfoPage.getContent(),
                brandInfoPage
        );
    }

//    @Override
//    public BrandDto findByName(String name) {
//        Brand brand = brandRepository.findByBrandNameContainingIgnoreCase(name).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        String.format("Brand Name = %s doesn't exist in db!",name))
//        );
//        return brandMapper.toBrandDto(brand);
//    }
//
//    @Override
//    public BrandDto findByUuid(String uuid) {
//        Brand brand = brandRepository.findByBrandUuid(uuid).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        String.format("Brand UUID = %s doesn't exist in db!",uuid)));
//
//        return brandMapper.toBrandDto(brand);
//    }

    @Transactional
    @Override
    public void updateByUuid(String uuid, BrandDto updateBrandDto) {
        // Step 1 : Check uuid of brand is exist in db
        if (brandRepository.existsByBrandUuid(uuid)){
            // Step 2 : Find brand by uuid
            Brand brand = brandRepository.findByBrandUuid(uuid).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Brand UUID = %s doesn't exist in db!",uuid)));
            // Step 3 : Update brand by Mapper
            brandMapper.fromBrandDto(brand,updateBrandDto);
            // Step 4 : Save brand
            brandRepository.save(brand);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Brand UUID = %s doesn't exist in db!",uuid));
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignBrandToCategory(String brandUuid, Integer categoryId) {
        assignOrUnAssignBrandToCategory(brandUuid, categoryId, true);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unAssignBrandFromCategory(String brandUuid, Integer categoryId) {
        assignOrUnAssignBrandToCategory(brandUuid, categoryId, false);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBrandToCategory(String brandUuid, Integer categoryId, UpdateBrandToCategoryDto updateBrandToCategoryDto) {
        // Retrieve category and brand entities, logging actions
        Category category = retrieveCategory(categoryId);
        Brand brand = retrieveBrand(brandUuid);

        // Check for existing brand assignment and handle accordingly
        if (category.getBrands().contains(brand)) {
            category.getBrands().remove(brand);
            log.info("Brand with ID {} unassigned from category with ID {} successfully.", brandUuid, categoryId);
        } else {
            log.error("Brand with ID {} not assigned to category with ID {}.", brandUuid, categoryId);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Brand not assigned to category.");
        }

        // Check for new brand assignment and handle accordingly
        if (updateBrandToCategoryDto.brandIds().isEmpty()) {
            log.info("No brands found in request body. Category with ID {} updated successfully.", categoryId);
        } else {
            Set<Brand> brands = updateBrandToCategoryDto.brandIds().stream()
                    .map(brandIdFromDto -> brandRepository.findById(brandIdFromDto).orElseThrow(() -> {
                        log.error("Brand with ID {} not found.", brandIdFromDto);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found.");
                    })).collect(Collectors.toSet());

            if (brands.contains(brand) || !Collections.disjoint(category.getBrands(), brands)) {
                log.error("Brand with ID {} cannot be assigned to category with ID {} as it is already assigned.", brandUuid, categoryId);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Brand already assigned to category.");
            }
            category.getBrands().addAll(brands);
            log.info("Brands with UUIDs {} assigned to category with ID {} successfully.", brands.stream().map(Brand::getBrandUuid).collect(Collectors.toList()), categoryId);
        }

        // Save the updated category
        categoryRepository.save(category);
    }
    private void assignOrUnAssignBrandToCategory(String brandUuid, Integer categoryId, boolean assign) {
        // Retrieve category and brand entities, logging actions
        Category category = retrieveCategory(categoryId);
        Brand brand = retrieveBrand(brandUuid);

        // Check for existing brand assignment and handle accordingly
        if (category.getBrands().contains(brand)) {
            if (assign) {
                log.error("Brand with ID {} already assigned to category with UUID {}.", brandUuid, categoryId);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Brand already assigned to category.");
            } else {
                category.getBrands().remove(brand);
                log.info("Brand with ID {} unassigned from category with ID {} successfully.", brandUuid, categoryId);
            }
        } else {
            if (assign) {
                category.getBrands().add(brand);
                log.info("Brand with ID {} assigned to category with ID {} successfully.", brandUuid, categoryId);
            } else {
                log.error("Brand with ID {} not assigned to category with ID {}.", brandUuid, categoryId);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Brand not assigned to category.");
            }
        }

        // Save the updated category
        categoryRepository.save(category);
    }

    private Category retrieveCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("Category with ID {} not found.", categoryId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        });
    }

    private Brand retrieveBrand(String brandUuid) {
        return brandRepository.findByBrandUuid(brandUuid).orElseThrow(() -> {
            log.error("Brand with UUID {} not found.", brandUuid);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found.");
        });
    }

}
