package co.devkh.onlinestore.reviewonlinestore.api.product;

import co.devkh.onlinestore.reviewonlinestore.api.product.web.CreateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.ProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.UpdateProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid){
        productService.deleteByUuid(uuid);
    }

    @GetMapping("/{uuid}")
    public ProductDto findByUuid(@PathVariable String uuid){
        return productService.findByUuid(uuid);
    }

    @GetMapping
    public List<ProductDto> findAll(){
        return productService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CreateProductDto createProductDto){
        //System.out.println(createProductDto);
        productService.createNew(createProductDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                             @RequestBody @Valid UpdateProductDto updateProductDto){
        System.out.println(updateProductDto);
        productService.updateByUuid(uuid,updateProductDto);
    }

}
