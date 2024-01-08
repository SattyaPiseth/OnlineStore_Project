package co.devkh.onlinestore.reviewonlinestore.api.product.data;

import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    // Derived Query Method : automatically generate query
    // follow structure of method name
    Optional<Category> findByNameContainingIgnoreCase(String name);
    //  Optional<Category> findByNameContainingIgnoreCase(String name);

    boolean existsCategoriesByNameContainingIgnoreCase(String name);

    Optional<Category> findCategoryById(Integer id);
}
