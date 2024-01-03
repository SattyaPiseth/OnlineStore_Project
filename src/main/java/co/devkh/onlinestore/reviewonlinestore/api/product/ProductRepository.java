package co.devkh.onlinestore.reviewonlinestore.api.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Boolean existsByUuid(String uuid);
    Optional<Product> findByUuid(String uuid);

    List<Product> findByNameContainingIgnoreCase(String pattern);
}
