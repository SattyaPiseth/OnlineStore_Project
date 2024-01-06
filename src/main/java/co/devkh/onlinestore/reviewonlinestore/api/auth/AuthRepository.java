package co.devkh.onlinestore.reviewonlinestore.api.auth;

import co.devkh.onlinestore.reviewonlinestore.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<User,Long> {
    @Modifying
    @Query("UPDATE User AS u SET u.verifiedCode = :verifiedCode WHERE u.username = :username")
    void updateVerifiedCode(@Param("username") String username,
                            @Param("verifiedCode") String verifiedCode);
    
    Optional<User> findByEmailAndVerifiedCodeAndIsDeletedFalse(String email,String verifiedCode);

    @Modifying
    @Query("UPDATE User AS u SET u.verifiedToken = :verifiedToken WHERE u.username = :username")
    void updateIsVerifiedToken(@Param("username") String username,@Param("verifiedToken") String verifiedToken);

    Optional<User> findByVerifiedTokenAndIsDeletedFalse(String verifiedToken);

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    boolean existsByVerifiedTokenAndIsDeletedFalse(String token);

}
