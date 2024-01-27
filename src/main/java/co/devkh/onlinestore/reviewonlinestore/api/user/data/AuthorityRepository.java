package co.devkh.onlinestore.reviewonlinestore.api.user.data;

import co.devkh.onlinestore.reviewonlinestore.api.user.projection.AuthorityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
    @Query("select a from Authority a where :query = 'ALL' or lower(a.name) like concat('%', lower(:query) , '%')")
    Page<AuthorityInfo> findByNameContains(@Param("query") String query, Pageable pageable);

}
