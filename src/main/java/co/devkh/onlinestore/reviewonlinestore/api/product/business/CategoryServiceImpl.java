package co.devkh.onlinestore.reviewonlinestore.api.product.business;

import co.devkh.onlinestore.reviewonlinestore.api.product.mapper.CategoryMapper;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.CategoryRepository;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Transactional
    @Override
    public void createNew(CategoryDto categoryDto) {
        Category category = categoryMapper.fromCategoryDto(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDtoList(categories);
    }

    @Override
    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByNameContainingIgnoreCase(name).orElseThrow();
        return categoryMapper.toCategoryDto(category);
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

    @Override
    public CategoryDto findById(Integer id) {
        Category category = categoryRepository.findCategoryById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Category Id = %d doesn't exist in db!",id))
        );
        return categoryMapper.toCategoryDto(category);
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
}
