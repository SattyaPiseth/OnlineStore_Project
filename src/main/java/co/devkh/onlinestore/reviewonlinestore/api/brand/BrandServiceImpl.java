package co.devkh.onlinestore.reviewonlinestore.api.brand;

import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import co.devkh.onlinestore.reviewonlinestore.api.brand.web.UpdateBrandToCategoryDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createNew(BrandDto brandDto) {
        Brand brand = brandMapper.fromBrandDto(brandDto);
        brand.setBrandUuid(UUID.randomUUID().toString());
        brandRepository.save(brand);
    }

    @Override
    public List<BrandDto> findAll() {
        return brandMapper.toBrandDtoList(brandRepository.findAll());
    }

    @Override
    public BrandDto findByName(String name) {
        Brand brand = brandRepository.findByBrandNameContainingIgnoreCase(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Brand Name = %s doesn't exist in db!",name))
        );
        return brandMapper.toBrandDto(brand);
    }

    @Override
    public BrandDto findByUuid(String uuid) {
        Brand brand = brandRepository.findByBrandUuid(uuid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Brand UUID = %s doesn't exist in db!",uuid)));

        return brandMapper.toBrandDto(brand);
    }

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
    public void assignBrandToCategory(Integer brandId, Integer categoryId) {
        assignOrUnAssignBrandToCategory(brandId, categoryId, true);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unAssignBrandFromCategory(Integer brandId, Integer categoryId) {
        assignOrUnAssignBrandToCategory(brandId, categoryId, false);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBrandToCategory(Integer brandId, Integer categoryId, UpdateBrandToCategoryDto updateBrandToCategoryDto) {
        // Retrieve category and brand entities, logging actions
        Category category = retrieveCategory(categoryId);
        Brand brand = retrieveBrand(brandId);

        // Check for existing brand assignment and handle accordingly
        if (category.getBrands().contains(brand)) {
            category.getBrands().remove(brand);
            log.info("Brand with ID {} unassigned from category with ID {} successfully.", brandId, categoryId);
        } else {
            log.error("Brand with ID {} not assigned to category with ID {}.", brandId, categoryId);
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
                log.error("Brand with ID {} cannot be assigned to category with ID {} as it is already assigned.", brandId, categoryId);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Brand already assigned to category.");
            }
            category.getBrands().addAll(brands);
            log.info("Brands with UUIDs {} assigned to category with ID {} successfully.", brands.stream().map(Brand::getBrandUuid).collect(Collectors.toList()), categoryId);
        }

        // Save the updated category
        categoryRepository.save(category);
    }
    private void assignOrUnAssignBrandToCategory(Integer brandId, Integer categoryId, boolean assign) {
        // Retrieve category and brand entities, logging actions
        Category category = retrieveCategory(categoryId);
        Brand brand = retrieveBrand(brandId);

        // Check for existing brand assignment and handle accordingly
        if (category.getBrands().contains(brand)) {
            if (assign) {
                log.error("Brand with ID {} already assigned to category with ID {}.", brandId, categoryId);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Brand already assigned to category.");
            } else {
                category.getBrands().remove(brand);
                log.info("Brand with ID {} unassigned from category with ID {} successfully.", brandId, categoryId);
            }
        } else {
            if (assign) {
                category.getBrands().add(brand);
                log.info("Brand with ID {} assigned to category with ID {} successfully.", brandId, categoryId);
            } else {
                log.error("Brand with ID {} not assigned to category with ID {}.", brandId, categoryId);
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

    private Brand retrieveBrand(Integer brandId) {
        return brandRepository.findById(brandId).orElseThrow(() -> {
            log.error("Brand with ID {} not found.", brandId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found.");
        });
    }

}
