package co.devkh.onlinestore.reviewonlinestore.api.user.data;

import co.devkh.onlinestore.reviewonlinestore.api.user.projection.RoleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    boolean existsByName(String name);
    boolean existsById(Integer id);

    @Query("""
            select r from Role r inner join r.authorities authorities
            where (:query = 'ALL' or (lower(r.name) like concat('%',:query,'%')) or (lower(authorities.name) like concat('%',lower(:query),'%')))""")
    Page<RoleInfo> findByNameContainsAndAuthorities_NameContains(@Param("query") String query, Pageable pageable);

}
