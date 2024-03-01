package co.devkh.onlinestore.reviewonlinestore.api.supplier.web;

import co.devkh.onlinestore.reviewonlinestore.api.supplier.data.Supplier;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.dto.NewSupplierDto;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.mapper.SupplierMapper;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.service.SupplierService;
import co.devkh.onlinestore.reviewonlinestore.base.controller.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sattya
 * create at 1/28/2024 11:08 PM
 */
@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController extends BaseController {
    private final SupplierService supplierService;

    @PreAuthorize("hasAuthority('SCOPE_product:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid NewSupplierDto newSupplierDto){
        Supplier supplier = SupplierMapper.INSTANCE.toSupplier(newSupplierDto);
        supplierService.createNew(supplier, newSupplierDto);
        SupplierMapper.INSTANCE.toNewSupplierDto(supplier);
    }
}
