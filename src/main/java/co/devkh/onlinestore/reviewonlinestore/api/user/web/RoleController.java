package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import co.devkh.onlinestore.reviewonlinestore.api.user.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;


    @GetMapping
    public List<NewRoleDto> findAllRoles() {
        return roleService.findAllRoles();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{roleId}")
    public void updateRole(@PathVariable Integer roleId,
                           @ModelAttribute @RequestBody @Valid NewRoleDto newRoleDto) {
        roleService.updateRole(roleId, newRoleDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewRole(@ModelAttribute @RequestBody @Valid NewRoleDto newRoleDto) {
        roleService.createRole(newRoleDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{roleId}/users/{userId}")
    public void assignRoleToUser(@PathVariable Integer roleId, @PathVariable Integer userId) {
        roleService.assignRoleToUser(userId, roleId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{roleId}/users/{userId}")
    public void removeRoleFromUser(@PathVariable Integer roleId, @PathVariable Integer userId) {
        roleService.removeRoleFromUser(userId, roleId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/users/{uuid}")
    public void updateRoleToUser(@PathVariable String uuid,
                                 @ModelAttribute @RequestBody @Valid UpdateRoleToUserDto updateRoleToUserDto) {
        roleService.updateRoleToUser(uuid, updateRoleToUserDto);
    }
}
