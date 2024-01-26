package co.devkh.onlinestore.reviewonlinestore.api.product.data;

import co.devkh.onlinestore.reviewonlinestore.api.product.projection.CategoryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer>, JpaSpecificationExecutor<Category> {
    // Derived Query Method : automatically generate query
    // follow structure of method name
    Optional<Category> findByNameContainingIgnoreCase(String name);
    //  Optional<Category> findByNameContainingIgnoreCase(String name);

    boolean existsCategoriesByNameContainingIgnoreCase(String name);

    Optional<Category> findCategoryById(Integer id);

    @Query("select c from Category c where :query = 'ALL' or lower(c.name) like concat('%', lower(:query), '%') or " +
            "lower(c.description) like concat('%', lower(:query), '%')")
    Page<CategoryInfo> findByNameStartsWithAndDescriptionLike(@Param("query") String query, Pageable pageable);
}
