package co.devkh.onlinestore.reviewonlinestore.api.product.web;

import co.devkh.onlinestore.reviewonlinestore.api.product.business.CategoryService;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CategoryDto;
import co.devkh.onlinestore.reviewonlinestore.base.controller.BaseController;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService categoryService;
    @PreAuthorize("hasAuthority('SCOPE_product:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CategoryDto categoryDto){
        categoryService.createNew(categoryDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping
    public ResponseEntity<StructureRS> findAll(BaseListingRQ request){
        return response(categoryService.getAll(request));
    }

    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> findById(@PathVariable Integer id){
        return response(categoryService.findById(id));
    }
    @PreAuthorize("hasAuthority('SCOPE_product:update')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateById(@PathVariable Integer id,
                           @RequestBody @Valid CategoryDto updateCategoryDto){
        categoryService.updateById(id,updateCategoryDto);

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{name}")
    public void deleteByName(@PathVariable @Valid String name){
        categoryService.deleteByName(name);
    }


}
