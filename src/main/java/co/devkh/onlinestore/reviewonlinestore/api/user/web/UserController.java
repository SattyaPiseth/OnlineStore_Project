package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import co.devkh.onlinestore.reviewonlinestore.api.user.UserService;
import co.devkh.onlinestore.reviewonlinestore.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    @ResponseStatus(HttpStatus.OK)
//    @PatchMapping("/{uuid}")
//    public void updateByUuid(@PathVariable String uuid,
//                             @RequestBody UpdateUserDto updateUserDto){
//        userService.updateByUuid(uuid,updateUserDto);
//    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication){

        return userService.me(authentication);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                             @RequestBody UpdateUserDto updateUserDto){
        userService.updateByUuid(uuid,updateUserDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewUser(@RequestBody @Valid NewUserDto newUserDto){
        userService.createNewUser(newUserDto);
    }

    @GetMapping("/{uuid}")
    public UserDto findUserByUuid(@PathVariable String uuid){
        return userService.findUserByUuid(uuid);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid){
        userService.deleteByUuid(uuid);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public void updateIsDeletedByUuid(@PathVariable String uuid,
                                      @RequestBody IsDeletedDto isDeletedDto){
        userService.updateIsDeletedByUuid(uuid,isDeletedDto.isDeleted());
    }

}
