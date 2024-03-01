package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.NewUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UpdateUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UserDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.security.CustomUserDetails;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {

    /**
     * Retrieve current logged in user
     * @param authentication of current log in user
     * @return UserDto
     */
    UserDto me(Authentication authentication);
    /**
     * This method is used to create a new user
     * resource into database
     * @param newUserDto is the request data from client
     */
    void createNewUser(NewUserDto newUserDto);

    /**
     * This method is used to update user by uuid
     * @param uuid of User
     * @param updateUserDto is the request data from client for update personal_information
     */
    // Update an existing user by uuid
    void updateByUuid(String uuid, UpdateUserDto updateUserDto);

    // Find users by pagination and filter

    /**
     * This method is used to retrieve resource user by uuid
     * from database
     * @param uuid of User
     * @return UserDto
     */
    // Find a user by UUID
    UserDto findUserByUuid(String uuid);

    /**
     * This method is used to delete user from database by uuid (not recommended)
     * @param uuid for delete a user(Permanently)
     */
    // delete a user by UUID (Permanently)
    void deleteByUuid(String uuid);

    /**
     * This method is used to update status(enable or disable)
     * for delete a user(Soft-Delete) by UUID
     * default of 'isDeleted' = false
     *
     * @param uuid      of User
     * @param isDeleted of User
     */
    // Update status : 'is_deleted' by UUID
    void updateIsDeletedByUuid(String uuid, Boolean isDeleted);

    /**
     * This method is used to retrieve all users
     * from database
     * @param request is the request data from client for pagination and filter
     * @return StructureRS
     */
    StructureRS findAllUsers(BaseListingRQ request);

    /**
     * This method is used to retrieve user by username
     * from database
     * @param username of User
     * @return CustomUserDetails
     */
    Optional<CustomUserDetails> findUserByUsername(String username);
}
