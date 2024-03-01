package co.devkh.onlinestore.reviewonlinestore.api.supplier.projection;

import java.util.List;

/**
 * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.supplier.data.Supplier}
 */
public interface SupplierInfo {
    String getUuid();

    String getCompanyName();

    String getContactName();

    String getContactTitle();

    String getContactEmail();

    String getAddress();

    String getCity();

    String getCountry();

    String getPhone();

    List<CategoryInfo> getCategories();

    /**
     * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.product.data.Category}
     */
    interface CategoryInfo {
        Integer getId();

        String getName();

        String getDescription();
    }
}