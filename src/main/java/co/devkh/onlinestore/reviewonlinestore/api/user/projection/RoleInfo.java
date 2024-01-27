package co.devkh.onlinestore.reviewonlinestore.api.user.projection;

import java.util.Set;

/**
 * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.user.data.Role}
 */
public interface RoleInfo {
    Integer getId();

    String getName();

    Set<AuthorityInfo> getAuthorities();

    /**
     * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.user.data.Authority}
     */
    interface AuthorityInfo {
        Integer getId();

        String getName();
    }
}