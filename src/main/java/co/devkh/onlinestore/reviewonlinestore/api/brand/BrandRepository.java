package co.devkh.onlinestore.reviewonlinestore.api.brand;

import co.devkh.onlinestore.reviewonlinestore.api.brand.web.BrandDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer>{
    Optional<Brand> findByBrandNameContainingIgnoreCase(String name);
    Optional<Brand> findByBrandUuid(String uuid);

    boolean existsByBrandUuid(String uuid);

}
