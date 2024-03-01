package co.devkh.onlinestore.reviewonlinestore.api.supplier.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {


    @Query("""
            select (count(s) > 0) from Supplier s
            where upper(s.companyName) = upper(:companyName) or upper(s.contactEmail) = upper(:contactEmail)""")
    boolean existsByCompanyNameOrContactEmailAllIgnoreCase(@Param("companyName") String companyName, @Param("contactEmail") String contactEmail);
}
