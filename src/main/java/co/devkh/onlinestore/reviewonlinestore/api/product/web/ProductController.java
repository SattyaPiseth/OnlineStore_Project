package co.devkh.onlinestore.reviewonlinestore.api.product.web;

import co.devkh.onlinestore.reviewonlinestore.api.product.business.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PreAuthorize("hasAuthority('SCOPE_product:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CreateProductDto createProductDto){
        //System.out.println(createProductDto);
        productService.createNew(createProductDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping
    public List<ProductDto> findAll(){
        return productService.findAll();
    }

    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping("/{uuid}")
    public ProductDto findByUuid(@PathVariable String uuid){
        return productService.findByUuid(uuid);
    }

    @PreAuthorize("hasAuthority('SCOPE_product:patch')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                             @RequestBody @Valid UpdateProductDto updateProductDto){
        System.out.println(updateProductDto);
        productService.updateByUuid(uuid,updateProductDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_product:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid){
        productService.deleteByUuid(uuid);
    }

    /**
     *  Search products by name, category, etc. (GET) api endpoint
     *  with pagination and sorting support (Pageable) and search term
     */
    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping("/search")
    public Page<ProductDto> searchProducts(@RequestParam (defaultValue = "",required = false)String searchTerm,
                               @RequestParam int page,@RequestParam int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productService.searchProducts(searchTerm,pageable);
    }

}
