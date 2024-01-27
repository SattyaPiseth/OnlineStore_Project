package co.devkh.onlinestore.reviewonlinestore.api.product.business;

import co.devkh.onlinestore.reviewonlinestore.api.brand.data.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.ProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.mapper.ProductMapper;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Product;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.ProductRepository;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.UpdateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.dto.CreateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.projection.ProductInfo;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.Supplier;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.base.service.BaseService;
import co.devkh.onlinestore.reviewonlinestore.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends BaseService implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Transactional
    @Override
    public void createNew(CreateProductDto createProductDto) {
        Product product = productMapper.fromCreateProductDto(createProductDto);
        product.setUuid(UUID.randomUUID().toString());
        product.setCode("PRO-"+ RandomUtil.generateCode());
        productRepository.save(product);

    }
    @Transactional
    @Override
    public void updateByUuid(String uuid, UpdateProductDto updateProductDto) {
      // Step 1: Check uuid of product in the database
        if (productRepository.existsByUuid(uuid)){

            // Step 2: Load old product
            Product product = productRepository.findByUuid(uuid).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Product UUID = %s doesn't exist in db!",uuid)));

            // Step 3: Map updating product partially
            productMapper.fromUpdateProductDto(product,updateProductDto);

            if (updateProductDto.categoryId() != null){
                Category newCategory = new Category();
                newCategory.setId(updateProductDto.categoryId());
                product.setCategory(newCategory);
            }
            if (updateProductDto.brandId() != null){
                Brand newBrand = new Brand();
                newBrand.setId(updateProductDto.brandId());
                product.setBrand(newBrand);
            }
            if (updateProductDto.supplierId() != null){
                Supplier newSupplier = new Supplier();
                newSupplier.setId(updateProductDto.supplierId());
                product.setSupplier(newSupplier);
            }

            // Step 4: Save latest product
            productRepository.save(product);

            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Product UUID = %s doesn't exist in db!",uuid));
    }
    @Transactional
    @Override
    public void deleteByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Product UUID = %s doesn't exist in db!",uuid))
        );
        productRepository.delete(product);
    }

    @Override
    public StructureRS findByUuid(String uuid,BaseListingRQ request) {
       Page<ProductDto> productDtoPage = productRepository.findByUuidContains(uuid,request.getPageable(request.getSort(),request.getOrder()))
               .map(productMapper::toProductDto);

       // handle exception
         if (productDtoPage.isEmpty()){
              throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                     String.format("Product UUID = %s doesn't exist in db!",uuid));
         }

       return response(productDtoPage.getContent(),productDtoPage);
    }

    @Override
    public StructureRS getAll(BaseListingRQ request) {
        Page<ProductInfo> productInfoPage = productRepository.findByNameStartsWithAndDescriptionStartsWith(request.getQuery(),request.getPageable(request.getSort(),request.getOrder()));
        return response(productInfoPage.getContent(),productInfoPage);
    }
}
