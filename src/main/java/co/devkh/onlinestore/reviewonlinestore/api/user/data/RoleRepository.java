package co.devkh.onlinestore.reviewonlinestore.api.user.data;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
