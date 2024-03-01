package co.devkh.onlinestore.reviewonlinestore.api.product.business;

import co.devkh.onlinestore.reviewonlinestore.api.product.mapper.CategoryMapper;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.CategoryRepository;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CategoryDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.projection.CategoryInfo;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl extends BaseService implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Transactional
    @Override
    public void createNew(CategoryDto categoryDto) {
        Category category = categoryMapper.fromCategoryDto(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public StructureRS findById(Integer id, BaseListingRQ request) {
        Page<CategoryInfo> categoryInfoPage = categoryRepository.selectById(id, request.getPageable(request.getSort(), request.getOrder()));
        if (categoryInfoPage.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Category Id = %d doesn't exist in db!",id));
        }
        return response(
                categoryInfoPage.getContent(),
                categoryInfoPage
        );
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        if (categoryRepository.existsCategoriesByNameContainingIgnoreCase(name)){
            Category category = categoryRepository.findByNameContainingIgnoreCase(name).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Category Name = %s doesn't exist in db!",name))
            );
            categoryRepository.delete(category);
        }
    }
    @Transactional
    @Override
    public void deleteById(Integer Id) {
        if (categoryRepository.existsById(Id)){
            Category category = categoryRepository.findCategoryById(Id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Category Id = %d doesn't exist in db!",Id))
            );
            categoryRepository.delete(category);
        }
    }

    @Transactional
    @Override
    public void updateById(Integer id, CategoryDto updateCategoryDto) {
        // Step 1: Check id of category in the database
        if (categoryRepository.existsById(id)){
           Category category =  categoryRepository.findCategoryById(id).orElseThrow(() ->
                     new ResponseStatusException(HttpStatus.NOT_FOUND,
                             String.format("Category ID = %d doesn't exist in db!",id)));
             categoryMapper.fromCategoryDto(category,updateCategoryDto);
             categoryRepository.save(category);
            return;
        }
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Category ID = %d doesn't exist in db!",id));
    }

    @Override
    public StructureRS getAll(BaseListingRQ request) {
        Page<CategoryInfo> categoryInfoPage = categoryRepository.findByNameStartsWithAndDescriptionLike(request.getQuery(), request.getPageable(request.getSort(), request.getOrder()));
        return response(
                categoryInfoPage.getContent(),
                categoryInfoPage
        );
    }
}
