package co.devkh.onlinestore.reviewonlinestore.api.product.projection;

import co.devkh.onlinestore.reviewonlinestore.api.brand.data.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.data.Supplier;

/**
 * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.product.data.Product}
 */
public interface ProductInfo {
    String getUuid();

    String getCode();

    String getName();

    String getDescription();

    String getImage();

    Double getUnitPrice();

    CategoryInfo getCategory();

    BrandInfo getBrand();

    SupplierInfo getSupplier();

    /**
     * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.product.data.Category}
     */
    interface CategoryInfo {
        String getName();
    }

    /**
     * Projection for {@link Brand}
     */
    interface BrandInfo {
        String getBrandName();
    }

    /**
     * Projection for {@link Supplier}
     */
    interface SupplierInfo {
        String getCompanyName();
    }
}