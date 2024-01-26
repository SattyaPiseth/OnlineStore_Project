package co.devkh.onlinestore.reviewonlinestore.api.brand.projection;

import java.util.Set;

/**
 * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.brand.data.Brand}
 */
public interface BrandInfo {
    String getBrandUuid();

    String getBrandName();

    Set<CategoryInfo> getCategories();

    /**
     * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.product.data.Category}
     */
    interface CategoryInfo {
        Integer getId();

        String getName();
    }
}