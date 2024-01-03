package co.devkh.onlinestore.reviewonlinestore.api.auth.web;

import co.devkh.onlinestore.reviewonlinestore.api.auth.AuthRepository;
import co.devkh.onlinestore.reviewonlinestore.api.auth.AuthService;
import co.devkh.onlinestore.reviewonlinestore.api.user.User;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Value("${app.base-uri}")
    private String appBaseUri;

    @PostMapping("/token")
    public AuthDto refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto){
        return authService.refreshToken(refreshTokenDto);
    }
    @PostMapping("/login")
    public AuthDto login(@Valid @RequestBody LoginDto loginDto){

        return authService.login(loginDto);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Map<String,String> register(@RequestBody @Valid  RegisterDto registerDto) throws MessagingException {
      String token =  authService.register(registerDto);

        return Map.of("message","Please check email and verify..!",
                "verify via 6 digits",appBaseUri+"auth/verify?email="+registerDto.email(),
                "verify via Verify Email Address",token);
    }

    @PostMapping("/verify")
    public Map<String,String> verify(@RequestBody @Valid VerifyDto verifyDto){
        authService.verify(verifyDto);

        return Map.of("message","Congratulation! Email has been verified..!");
    }

    @GetMapping("/verify")
    public Map<String,String> verify(@RequestParam("token") String token){
        authService.verifyUser(token);
        return Map.of("message","Congratulation! Email has been verified..!");
    }
}
