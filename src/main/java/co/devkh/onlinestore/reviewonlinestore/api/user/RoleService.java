package co.devkh.onlinestore.reviewonlinestore.api.user;

import co.devkh.onlinestore.reviewonlinestore.api.user.web.NewRoleDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.UpdateRoleToUserDto;

import java.util.List;

public interface RoleService {

    /**
     * Create a new role
     * @param newRoleDto the new role to create
     */
    void createRole(NewRoleDto newRoleDto);

    /**
     * Delete a role
     * @param roleId the role id to delete
     */
    void deleteRole(Integer roleId);

    /**
     * Update a role
     * @param roleId the role id to update
     * @param newRoleDto the new role data
     */
    void updateRole(Integer roleId, NewRoleDto newRoleDto);

    /**
     * Assign a role to a user
     * @param userId the user id
     * @param roleId the role id
     */
    void assignRoleToUser(Integer userId, Integer roleId);

    /**
     * Remove a role from a user
     * @param userId the user id
     * @param roleId the role id
     */
    void removeRoleFromUser(Integer userId, Integer roleId);

    /**
     * Update a role to a user
     * @param uuid the user uuid
     * @param updateRoleToUserDto the new role to update
     */
    void updateRoleToUser(String uuid, UpdateRoleToUserDto updateRoleToUserDto);

    /**
     * Find all roles
     * @return the list of roles
     */
    List<NewRoleDto> findAllRoles();
}
