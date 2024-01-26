package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import co.devkh.onlinestore.reviewonlinestore.api.user.service.RoleService;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.role_dto.NewRoleDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.role_dto.RoleDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UpdateRoleToUserDto;
import co.devkh.onlinestore.reviewonlinestore.base.controller.BaseController;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController {
    private final RoleService roleService;
    @PreAuthorize("hasAuthority('SCOPE_role:read')")
    @GetMapping
    public ResponseEntity<StructureRS> findAllRoles(@RequestParam(value = "page", required = false) int page, @RequestParam int size){
        var pageable = Pageable.ofSize(size).withPage(page);
        Page<RoleDto> newRoleDtoPage = roleService.findAllRoles(pageable);
        return response(newRoleDtoPage);
    }
    @PreAuthorize("hasAuthority('SCOPE_role:patch')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{roleId}")
    public void updateRole(@PathVariable Integer roleId,
                           @ModelAttribute @RequestBody @Valid NewRoleDto newRoleDto) {
        roleService.updateRole(roleId, newRoleDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_role:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewRole(@ModelAttribute @RequestBody @Valid NewRoleDto newRoleDto) {
        roleService.createRole(newRoleDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_role:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
    }
    @PreAuthorize("hasAuthority('SCOPE_role:write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{roleId}/users/{userUuid}")
    public void assignRoleToUser(@PathVariable Integer roleId, @PathVariable String userUuid) {
        roleService.assignRoleToUser(userUuid, roleId);
    }
    @PreAuthorize("hasAuthority('SCOPE_role:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{roleId}/users/{userUuid}")
    public void removeRoleFromUser(@PathVariable Integer roleId, @PathVariable String userUuid) {
        roleService.removeRoleFromUser(userUuid, roleId);
    }
    @PreAuthorize("hasAuthority('SCOPE_role:update')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/users/{uuid}")
    public void updateRoleToUser(@PathVariable String uuid,
                                 @ModelAttribute @RequestBody @Valid UpdateRoleToUserDto updateRoleToUserDto) {
        roleService.updateRoleToUser(uuid, updateRoleToUserDto);
    }
}
