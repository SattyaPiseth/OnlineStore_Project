package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.Role;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.RoleRepository;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.User;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.UserRepository;
import co.devkh.onlinestore.reviewonlinestore.api.user.mapper.RoleMapper;
import co.devkh.onlinestore.reviewonlinestore.api.user.projection.RoleInfo;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.role_dto.NewRoleDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UpdateRoleToUserDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl extends BaseService implements RoleService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional(rollbackFor = Exception.class) // Ensure rollback for all exceptions
    @Override
    public void createRole(NewRoleDto newRoleDto) {

        // Check for existing role name
        if (roleRepository.existsByName(newRoleDto.name())) { // Use getName() for clarity
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exists.");
        }

        // Create and save the role
        Role newRole = roleMapper.fromNewRoleDto(newRoleDto);
        roleRepository.save(newRole);
    }

    @Transactional(rollbackFor = Exception.class) // Ensure rollback for all exceptions
    @Override
    public void deleteRole(Integer roleId) {

        // Check for existing role using a descriptive variable name
        boolean roleExists = roleRepository.existsById(roleId);
        if (!roleExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found.");
        }

        // Delete the role, potentially logging the action
        roleRepository.deleteById(roleId);
        log.info("Role with ID {} deleted successfully.", roleId);
    }


    @Transactional(rollbackFor = Exception.class) // Ensure rollback for all exceptions
    @Override
    public void updateRole(Integer roleId, NewRoleDto newRoleDto) {

        // Retrieve the role, potentially logging the action
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));
        log.info("Updating role with ID {}", roleId);

        // Map DTO properties to the role entity
        roleMapper.fromNewRoleDto(newRoleDto, role);

        // Save the updated role
        roleRepository.save(role);
        log.info("Role with ID {} updated successfully.", roleId);
    }


    @Transactional(rollbackFor = Exception.class) // Ensure rollback for all exceptions
    @Override
    public void assignRoleToUser(String userUuid, Integer roleId) {

        // Retrieve user and role entities, potentially logging actions
        User user = userRepository.selectUserByUuidAndIsDeleted(userUuid,false)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        log.info("Assigning role to user with UUID {}", userUuid);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));

        // Check for existing role assignment
        if (user.getRoles().contains(role)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has the role.");
        }

        // Add the role to the user's collection
        user.getRoles().add(role);

        // Save the updated user
        userRepository.save(user);
        log.info("Role assigned to user successfully.");
    }

    @Transactional(rollbackFor = Exception.class) // Ensure rollback for all exceptions
    @Override
    public void removeRoleFromUser(String userUuid, Integer roleId) {

            // Retrieve user and role entities, potentially logging actions
            User user = userRepository.selectUserByUuidAndIsDeleted(userUuid,false)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
            log.info("Removing role from user with UUID {}", userUuid);

            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));

            // Check for existing role assignment
            if (!user.getRoles().contains(role)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User does not have the role.");
            }

            // Remove the role from the user's collection
            user.getRoles().remove(role);

            // Save the updated user
            userRepository.save(user);
            log.info("Role removed from user successfully.");
    }


    @Transactional
    @Override
    public void updateRoleToUser(String uuid, UpdateRoleToUserDto updateRoleToUserDto) {

            // Retrieve the user, potentially logging the action
            User user = userRepository.selectUserByUuidAndIsDeleted(uuid,false)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
            log.info("Updating role to user with uuid {}",uuid);

           if (updateRoleToUserDto.roleIds().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one role ID must be provided.");
            }

            // Retrieve the roles, potentially logging the action
            Set<Role> roles = updateRoleToUserDto.roleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found.")))
                    .collect(Collectors.toSet());

            // Update the user's roles
            user.setRoles(roles);

            // Save the updated user
            userRepository.save(user);
            log.info("Role updated to user successfully.");
    }

    @Override
    public StructureRS getAllRoles(BaseListingRQ request) {
        Page<RoleInfo> roleInfoPage = roleRepository.findByNameContainsAndAuthorities_NameContains(request.getQuery(),request.getPageable(request.getSort(),request.getOrder()));
        return response(roleInfoPage.getContent(), roleInfoPage);
    }

//    @Override
//    public Page<RoleDto> findAllRoles(Pageable pageable) {
//        return roleRepository.findAll(pageable).map(roleMapper::toRoleDto);
//    }
}
