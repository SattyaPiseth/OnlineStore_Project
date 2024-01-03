package co.devkh.onlinestore.reviewonlinestore.api.product;

import co.devkh.onlinestore.reviewonlinestore.api.product.web.CategoryDto;
import com.sun.mail.imap.protocol.ID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CategoryDto categoryDto){
        categoryService.createNew(categoryDto);
    }

    @GetMapping
    public List<CategoryDto> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Integer id){
        return categoryService.findById(id);
    }

    @GetMapping("/find/{cateName}")
    public CategoryDto findByName(@PathVariable String cateName){
        return categoryService.findByName(cateName);
    }

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
