package co.devkh.onlinestore.reviewonlinestore.api.product.web;

import co.devkh.onlinestore.reviewonlinestore.api.product.business.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PreAuthorize("hasAuthority('SCOPE_product:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CategoryDto categoryDto){
        categoryService.createNew(categoryDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping
    public List<CategoryDto> findAll(){
        return categoryService.findAll();
    }
    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Integer id){
        return categoryService.findById(id);
    }
    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping("/find/{cateName}")
    public CategoryDto findByName(@PathVariable String cateName){
        return categoryService.findByName(cateName);
    }

    @PreAuthorize("hasAuthority('SCOPE_product:update')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateById(@PathVariable Integer id,
                           @RequestBody @Valid CategoryDto updateCategoryDto){
        categoryService.updateById(id,updateCategoryDto);

    }
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Integer id){
//        categoryService.deleteById(id);
//    }
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/delete/{name}")
//    public void deleteByName(@PathVariable @Valid String name){
//        categoryService.deleteByName(name);
//    }


}
