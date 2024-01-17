package co.devkh.onlinestore.reviewonlinestore.api.brand.web;

import co.devkh.onlinestore.reviewonlinestore.api.brand.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid BrandDto brandDto){
        brandService.createNew(brandDto);
    }

    @GetMapping
    public List<BrandDto> findAll(){
        return brandService.findAll();
    }

    @GetMapping("/{uuid}")
    public BrandDto findByUuid(@PathVariable String uuid){
        return brandService.findByUuid(uuid);
    }

    @GetMapping("/find/{brandName}")
    public BrandDto findByName(@PathVariable String brandName){
        return brandService.findByName(brandName);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                          @ModelAttribute @RequestBody @Valid BrandDto updateBrandDto){
        brandService.updateByUuid(uuid,updateBrandDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{brandId}/categories/{categoryId}")
    public void assignBrandToCategory(@PathVariable Integer brandId, @PathVariable Integer categoryId){
        brandService.assignBrandToCategory(brandId,categoryId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{brandId}/categories/{categoryId}")
    public void unAssignBrandFromCategory(@PathVariable Integer brandId, @PathVariable Integer categoryId){
        brandService.unAssignBrandFromCategory(brandId,categoryId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{brandId}/categories/{categoryId}")
    public void updateBrandToCategory(@PathVariable Integer brandId, @PathVariable Integer categoryId,
                                    @ModelAttribute @RequestBody @Valid UpdateBrandToCategoryDto updateBrandToCategoryDto){
        brandService.updateBrandToCategory(brandId,categoryId,updateBrandToCategoryDto);
    }
}
