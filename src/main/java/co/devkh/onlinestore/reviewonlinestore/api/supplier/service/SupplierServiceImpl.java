package co.devkh.onlinestore.reviewonlinestore.api.supplier.service;

import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.CategoryRepository;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.data.Supplier;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.data.SupplierRepository;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.dto.NewSupplierDto;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.dto.updateSupplierDto;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.mapper.SupplierMapper;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sattya
 * create at 1/28/2024 11:06 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl extends BaseService implements SupplierService{
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    @Override
    public void createNew(Supplier supplier, NewSupplierDto newSupplierDto) {
        // check if supplier name or contact email already exists
        if (supplierRepository.existsByCompanyNameOrContactEmailAllIgnoreCase(newSupplierDto.companyName(), newSupplierDto.contactEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Supplier Name = %s or Contact Email = %s already exists in the database.",
                            newSupplierDto.companyName(), newSupplierDto.contactEmail()));
        }
        supplier.setUuid(UUID.randomUUID().toString());
        assignCategoriesToSupplier(supplier, newSupplierDto);
        supplierRepository.save(supplier);
    }

    private void assignCategoriesToSupplier(Supplier supplier, NewSupplierDto newSupplierDto) {
        Set<Category> categories = newSupplierDto.categoriesIds()
                .stream()
                .map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        if (categories.size() != newSupplierDto.categoriesIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more categories not found.");
        }

        supplier.setCategories(new ArrayList<>(categories));
    }
    @Override
    public StructureRS findAll(BaseListingRQ request) {
        return null;
    }

    @Override
    public StructureRS findByUuid(String uuid, BaseListingRQ request) {
        return null;
    }

    @Override
    public void updateByUuid(String uuid, updateSupplierDto updateSupplierDto) {

    }

    @Override
    public void deleteByUuid(String uuid) {

    }
}
