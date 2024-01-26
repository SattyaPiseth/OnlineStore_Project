package co.devkh.onlinestore.reviewonlinestore.api.user.data;

import co.devkh.onlinestore.reviewonlinestore.api.user.projection.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // SQL = SELECT * FROM users WHERE uuid = #{uuid}
    // JPQL = Jakarta or Java Persistent Query Language
    // JPQL is Spring Data feature for DAO implementation with SQL-concept
    // JPQL is based-on Entity
    // There are 2 ways for binding parameter
    // 1. ':' Named Binding Parameter
    // 2. '?' Positional Binding Parameter
    @Query("SELECT u FROM User AS u WHERE u.uuid = :uuid")
    Optional<User> selectUserByUuid(@Param("uuid") String uuid);

    // Native Query
    @Query(value = "SELECT * FROM users WHERE uuid = ?1 AND is_deleted = ?2",
            nativeQuery = true)
    Optional<User> selectUserByUuidAndIsDeleted(String uuid, Boolean isDeleted);

    // Boolean existsByUuid(String uuid) // Derived Query Method
//    @Query("SELECT EXISTS (SELECT u FROM User AS u WHERE u.uuid = ?1) ")
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.uuid = ?1")

    Boolean checkUserByUuid(String uuid);
    @Modifying
    @Query("UPDATE User AS u SET u.isDeleted = :isDeleted WHERE u.uuid = :uuid")
    void updateIsDeletedStatusByUuid(@Param("uuid") String uuid,@Param("isDeleted") Boolean isDeleted);

    // Auto Derived Query Method

    Boolean existsByEmailAndIsDeletedFalse(String email);

    Optional<User> findByUsernameAndIsDeletedFalseAndIsVerifiedTrue(String username);

    boolean existsByUsernameOrEmailAndIsDeletedFalse(String username, String email);
    @Query("SELECT u FROM User u INNER JOIN u.roles roles WHERE (:query = 'ALL' OR LOWER(u.uuid) LIKE %:query% OR LOWER(u.username) LIKE %:query% OR LOWER(u.email) LIKE %:query% or lower(roles.name) LIKE %:query%) and u.isVerified = true and u.isDeleted = false")
    Page<UserInfo> findByUuidStartsWithAndUsernameContainsAndEmailContainsAndRoles_NameContains(@Param("query") String query, Pageable pageable);
}
