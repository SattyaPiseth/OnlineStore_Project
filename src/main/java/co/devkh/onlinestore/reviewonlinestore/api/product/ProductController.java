package co.devkh.onlinestore.reviewonlinestore.api.product;

import co.devkh.onlinestore.reviewonlinestore.api.product.web.CreateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.ProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.UpdateProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CreateProductDto createProductDto){
        //System.out.println(createProductDto);
        productService.createNew(createProductDto);
    }

    @GetMapping
    public List<ProductDto> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{uuid}")
    public ProductDto findByUuid(@PathVariable String uuid){
        return productService.findByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                             @RequestBody @Valid UpdateProductDto updateProductDto){
        System.out.println(updateProductDto);
        productService.updateByUuid(uuid,updateProductDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid){
        productService.deleteByUuid(uuid);
    }

    @GetMapping("/search")
    public List<ProductDto> searchProducts(@RequestParam (defaultValue = "")String searchTerm){
        return productService.searchProducts(searchTerm);
    }
}
