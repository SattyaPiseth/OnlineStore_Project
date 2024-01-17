package co.devkh.onlinestore.reviewonlinestore.api.product.business;

import co.devkh.onlinestore.reviewonlinestore.api.brand.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.product.mapper.ProductMapper;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Product;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.ProductRepository;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.UpdateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.CreateProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.product.web.ProductDto;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.Supplier;
import co.devkh.onlinestore.reviewonlinestore.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
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
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();

        return productMapper.toProductDtoList(products);
    }

    @Override
    public ProductDto findByUuid(String uuid) {
       return productMapper.toProductDto(productRepository.findByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
               String.format("Product UUID = %s doesn't exist in db!",uuid))));
    }

    @Override
    public Page<ProductDto> searchProducts(String searchTerm, Pageable pageable) {

        if (!searchTerm.isEmpty()){
            return productRepository.findByNameContainingIgnoreCase(searchTerm,pageable).map(productMapper::toProductDto);
        }else {
            return productRepository.findAll(pageable).map(productMapper::toProductDto);
        }
    }
}
