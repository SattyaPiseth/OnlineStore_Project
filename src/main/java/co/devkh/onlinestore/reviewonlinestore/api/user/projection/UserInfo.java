package co.devkh.onlinestore.reviewonlinestore.api.user.projection;

import java.util.Set;

/**
 * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.user.data.User}
 */
public interface UserInfo {
    String getUuid();

    String getUsername();

    String getEmail();

    String getNickName();

    Set<RoleInfo> getRoles();

    /**
     * Projection for {@link co.devkh.onlinestore.reviewonlinestore.api.user.data.Role}
     */
    interface RoleInfo {
        Integer getId();

        String getName();
    }
}