package co.devkh.onlinestore.reviewonlinestore.api.product.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Boolean existsByUuid(String uuid);
    Optional<Product> findByUuid(String uuid);

    Page<Product> findByNameContainingIgnoreCase(String pattern, Pageable pageable);

}
