package co.devkh.onlinestore.reviewonlinestore.api.product.projection;

import co.devkh.onlinestore.reviewonlinestore.api.brand.data.Brand;

import java.util.List;

/**
 * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.product.data.Category}
 */
public interface CategoryInfo {
    Integer getId();

    String getName();

    String getDescription();

    List<BrandInfo> getBrands();

    /**
     * Projection for {@link Brand}
     */
    interface BrandInfo {
        String getBrandUuid();

        String getBrandName();
    }
}