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

//    @Override
//    public Page<CategoryDto> findAll(Map<String,String> params, int page, int size) {
////        List<Category> categories = categoryRepository.findAll();
////        categoryMapper.toCategoryDtoList(categories);
////        return categoryMapper.toCategoryDtoList(categories);
//        CategoryFilter categoryFilter = new CategoryFilter();
//        if (params.containsKey("name")){
//            categoryFilter.setName(params.get("name"));
//        }
//        if (params.containsKey("id")){
//            categoryFilter.setId(Integer.parseInt(params.get("id")));
//            try{
//                categoryFilter.setId(Integer.parseInt(params.get("id")));
//            }catch (NumberFormatException e){
//                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
//                        String.format("Category ID = %s is not number",params.get("id")));
//            }
//        }
//
//        // TODO : Add paging and sorting
//        CategorySpec categorySpec = new CategorySpec(categoryFilter);
//        Pageable pageable = PageUtil.getPageable(page,size);
//        Page<Category> pageCategory = categoryRepository.findAll(categorySpec,pageable);
//        return pageCategory.map(categoryMapper::toCategoryDto);
//    }


//    @Override
//    public CategoryDto findByName(String name) {
//        Category category = categoryRepository.findByNameContainingIgnoreCase(name).orElseThrow();
//        return categoryMapper.toCategoryDto(category);
//    }


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

    @Override
    public StructureRS getAll(BaseListingRQ request) {
        Page<CategoryInfo> categoryInfoPage = categoryRepository.findByNameStartsWithAndDescriptionLike(request.getQuery(), request.getPageable(request.getSort(), request.getOrder()));
        return response(
                categoryInfoPage.getContent(),
                categoryInfoPage
        );
    }
}
