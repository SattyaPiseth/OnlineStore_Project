package co.devkh.onlinestore.reviewonlinestore.api.product.data;

import co.devkh.onlinestore.reviewonlinestore.api.product.projection.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Boolean existsByUuid(String uuid);
    Optional<Product> findByUuid(String uuid);

    @Query("select p from Product p where :query = 'ALL' or lower(p.code) like concat('%', lower(:query), '%') or " +
                "lower(p.name) like concat('%', lower(:query), '%') or lower(p.uuid) like concat('%', lower(:query), '%') ")
    Page<ProductInfo> findByNameStartsWithAndDescriptionStartsWith(@Param("query") String query, Pageable pageable);

    @Query("select p from Product p where p.uuid like concat('%', :uuid, '%')")
    Page<Product> findByUuidContains(@Param("uuid") String uuid, Pageable pageable);


}
