package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import co.devkh.onlinestore.reviewonlinestore.api.auth.AuthService;
import co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto.RegisterDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.service.UserService;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.IsDeletedDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UpdateUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UserDto;
import co.devkh.onlinestore.reviewonlinestore.base.controller.BaseController;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final AuthService authService;
    private final UserService userService;
    @Value("${app.base-uri}")
    private String appBaseUri;
    @PreAuthorize("hasAuthority('SCOPE_user:profile')")
    @GetMapping("/me")
    public ResponseEntity<StructureRS> me(Authentication authentication){
        return response(userService.me(authentication));
    }
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping
    public ResponseEntity<StructureRS> findAll(BaseListingRQ request){
        return response(userService.findAllUsers(request));
    }
    @PreAuthorize("hasAuthority('SCOPE_user:patch')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public ResponseEntity<StructureRS> updateByUuid(@PathVariable String uuid,
                                                    @ModelAttribute @RequestBody @Valid UpdateUserDto updateUserDto){
        userService.updateByUuid(uuid,updateUserDto);
        return response( Map.of("message","Successfully updated..!") );
    }

    @PreAuthorize("hasAuthority('SCOPE_user:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<StructureRS> createNewUser(@RequestBody @Valid RegisterDto newUserDto) throws MessagingException {
      String token =  authService.register(newUserDto);

        return response(Map.of("message","Please check email and verify..!",
                "verify via 6 digits",appBaseUri+"auth/verify?email="+newUserDto.email(),
                "verify via Verify Email Address",token));
    }
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping("/{uuid}")
    public ResponseEntity<StructureRS> findUserByUuid(@PathVariable String uuid){
        return response(userService.findUserByUuid(uuid));
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
    public ResponseEntity<StructureRS> updateIsDeletedByUuid(@PathVariable String uuid,
                                                             @ModelAttribute @RequestBody @Valid IsDeletedDto isDeletedDto){
        userService.updateIsDeletedByUuid(uuid,isDeletedDto.isDeleted());
        return response( Map.of("message","Successfully updated..!"));
    }
}
