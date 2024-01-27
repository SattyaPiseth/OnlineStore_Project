package co.devkh.onlinestore.reviewonlinestore.api.product.web;

import co.devkh.onlinestore.reviewonlinestore.api.product.business.ProductService;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.*;
import co.devkh.onlinestore.reviewonlinestore.base.controller.BaseController;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {
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
    public ResponseEntity<StructureRS> findAll(BaseListingRQ request){
        return response(productService.getAll(request));
    }
    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping("/{uuid}")
    public ResponseEntity<StructureRS> findByUuid(@PathVariable String uuid,BaseListingRQ request){
        return response(productService.findByUuid(uuid,request));
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
//    @PreAuthorize("hasAuthority('SCOPE_product:read')")
//    @GetMapping("/search")
//    public ResponseEntity<StructureRS> searchProducts(@RequestParam (defaultValue = "",required = false)String searchTerm,
//                                                      @RequestParam int page, @RequestParam int size){
//
//        var pageable = Pageable.ofSize(size).withPage(page);
////        return productService.searchProducts(searchTerm,pageable);
//        Page<ProductDto> productDtoPage = productService.searchProducts(searchTerm,pageable);
//        return response(productDtoPage);
//    }
}
