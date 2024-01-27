package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.api.user.web.role_dto.NewRoleDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UpdateRoleToUserDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;


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
     * @param userUuid the user id
     * @param roleId the role id
     */
    void assignRoleToUser(String userUuid, Integer roleId);

    /**
     * Remove a role from a user
     * @param userUuid the user id
     * @param roleId the role id
     */
    void removeRoleFromUser(String userUuid, Integer roleId);

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
//    Page<RoleDto> findAllRoles(Pageable pageable);
    StructureRS getAllRoles(BaseListingRQ request);
}
