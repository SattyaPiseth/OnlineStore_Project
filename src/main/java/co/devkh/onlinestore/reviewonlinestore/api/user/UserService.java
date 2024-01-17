package co.devkh.onlinestore.reviewonlinestore.api.user;

import co.devkh.onlinestore.reviewonlinestore.api.user.web.NewUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.UpdateUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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
     * This method is used to delete user from database by uuid
     * @param uuid of User
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

    Page<UserDto> findAllUsers(Pageable pageable);
}
