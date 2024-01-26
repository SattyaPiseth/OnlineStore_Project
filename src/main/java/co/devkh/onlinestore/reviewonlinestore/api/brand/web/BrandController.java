package co.devkh.onlinestore.reviewonlinestore.api.brand.web;

import co.devkh.onlinestore.reviewonlinestore.api.brand.service.BrandService;
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
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController extends BaseController {
    private final BrandService brandService;
    @PreAuthorize("hasAuthority('SCOPE_product:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@ModelAttribute @RequestBody @Valid NewBrandDto newBrandDto){
        brandService.createNew(newBrandDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_product:read')")
    @GetMapping
    public ResponseEntity<StructureRS> findAll(BaseListingRQ request){
        return response(brandService.findAll(request));
    }
//    @PreAuthorize("hasAuthority('SCOPE_product:read')")
//    @GetMapping("/{uuid}")
//    public ResponseEntity<StructureRS> findByUuid(@PathVariable String uuid){
//        return response(brandService.findByUuid(uuid));
//    }
//    @PreAuthorize("hasAuthority('SCOPE_product:read')")
//    @GetMapping("/find/{brandName}")
//    public ResponseEntity<StructureRS> findByName(@PathVariable String brandName){
////        return brandService.findByName(brandName);
//        return response(brandService.findByName(brandName));
//    }

    @PreAuthorize("hasAuthority('SCOPE_product:update')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                          @ModelAttribute @RequestBody @Valid BrandDto updateBrandDto){
        brandService.updateByUuid(uuid,updateBrandDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_product:write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{brandUuid}/categories/{categoryId}")
    public void assignBrandToCategory(@PathVariable String brandUuid, @PathVariable Integer categoryId){
        brandService.assignBrandToCategory(brandUuid,categoryId);
    }

    @PreAuthorize("hasAuthority('SCOPE_product:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{brandUuid}/categories/{categoryId}")
    public void unAssignBrandFromCategory(@PathVariable String brandUuid, @PathVariable Integer categoryId){
        brandService.unAssignBrandFromCategory(brandUuid,categoryId);
    }
    @PreAuthorize("hasAuthority('SCOPE_product:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{brandUuid}/categories/{categoryId}")
    public void updateBrandToCategory(@PathVariable String brandUuid, @PathVariable Integer categoryId,
                                    @ModelAttribute @RequestBody @Valid UpdateBrandToCategoryDto updateBrandToCategoryDto){
        brandService.updateBrandToCategory(brandUuid,categoryId,updateBrandToCategoryDto);
    }
}
