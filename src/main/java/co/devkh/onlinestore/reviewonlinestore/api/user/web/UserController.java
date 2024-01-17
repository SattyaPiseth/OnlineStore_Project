package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import co.devkh.onlinestore.reviewonlinestore.api.auth.AuthService;
import co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto.RegisterDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    @PreAuthorize("hasAuthority('SCOPE_user:profile')")
    @GetMapping("/me")
    public UserDto me(Authentication authentication){

        return userService.me(authentication);
    }
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping
    public Page<UserDto> findAll(@RequestParam(value = "page", required = false) int page,@RequestParam int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return userService.findAllUsers(pageable);
    }
    @PreAuthorize("hasAuthority('SCOPE_user:patch')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public Map<String,Object> updateByUuid(@PathVariable String uuid,
                             @ModelAttribute @RequestBody @Valid UpdateUserDto updateUserDto){
        userService.updateByUuid(uuid,updateUserDto);
        return Map.of("message","Successfully updated..!");
    }

    @PreAuthorize("hasAuthority('SCOPE_user:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewUser(@RequestBody @Valid RegisterDto newUserDto) throws MessagingException {
        authService.register(newUserDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping("/{uuid}")
    public UserDto findUserByUuid(@PathVariable String uuid){
        return userService.findUserByUuid(uuid);
    }
    @PreAuthorize("hasAuthority('SCOPE_user:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid){
        userService.deleteByUuid(uuid);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:patch')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public Map<String,Object> updateIsDeletedByUuid(@PathVariable String uuid,
                                    @ModelAttribute @RequestBody @Valid IsDeletedDto isDeletedDto){

        userService.updateIsDeletedByUuid(uuid,isDeletedDto.isDeleted());
        return Map.of("message","Successfully updated..!");
    }
}
