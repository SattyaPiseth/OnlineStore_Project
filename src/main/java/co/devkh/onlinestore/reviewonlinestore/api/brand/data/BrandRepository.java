package co.devkh.onlinestore.reviewonlinestore.api.brand.data;

import co.devkh.onlinestore.reviewonlinestore.api.brand.projection.BrandInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer>{
    Optional<Brand> findByBrandNameContainingIgnoreCase(String name);
    Optional<Brand> findByBrandUuid(String uuid);

    boolean existsByBrandUuid(String uuid);

    boolean existsByBrandName(String s);

    @Query("select b from Brand b where ?1 = 'ALL' or lower(b.brandUuid) like concat('%',lower(?1),'%') or lower(b.brandName) like concat('%', lower(?1),'%' )")
    Page<BrandInfo> findByBrandUuidStartsWithAndBrandNameStartsWith(@Param("query") String query, Pageable pageable);

}
