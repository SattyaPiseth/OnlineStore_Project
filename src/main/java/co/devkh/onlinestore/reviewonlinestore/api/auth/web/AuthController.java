package co.devkh.onlinestore.reviewonlinestore.api.auth.web;

import co.devkh.onlinestore.reviewonlinestore.api.auth.AuthService;
import co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Value("${app.base-uri}")
    private String appBaseUri;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/token")
    public AuthDto refreshToken(@ModelAttribute @Valid @RequestBody RefreshTokenDto refreshTokenDto){
        return authService.refreshToken(refreshTokenDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthDto login(@ModelAttribute @Valid @RequestBody LoginDto loginDto){
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verify")
    public Map<String,String> verify(@ModelAttribute @RequestBody @Valid VerifyDto verifyDto){
        authService.verify(verifyDto);
        return Map.of("message","Congratulation! Email has been verified..!");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/verify")
    public Map<String,String> verify(@RequestParam("token") String token){
        authService.verifyUser(token);
        return Map.of("message","Congratulation! Email has been verified..!");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/forgot-password")
    public Map<String,Object> forgotPassword(@ModelAttribute @RequestBody @Valid ForgotPasswordDto forgotPasswordDto) throws MessagingException {
        authService.forgotPassword(forgotPasswordDto);
        return Map.of("message","We have sent a reset password link to your email",
                "reset_password_link",authService.forgotPassword(forgotPasswordDto));
    }

    @GetMapping("/reset-password")
    public Map<String,String> verifyResetToken(@RequestParam("token") String token){
        // Validate token and handle errors
        boolean isValidToken = authService.verifyResetToken(token);
        if (!isValidToken) {
            return Map.of("message","Invalid password reset token");
        }
        return Map.of("message","Token is valid",
                "reset_token",token);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("token") String token,
            @ModelAttribute @RequestBody @Valid ResetPasswordDto resetPasswordDto
    ) {
        // Validate token and handle errors
        if (!authService.verifyResetToken(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password reset token");
        }

        authService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok(Map.of("message", "Your password has been reset successfully"));
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/change-password")
    public ResponseEntity<Object> changePassword(@ModelAttribute @RequestBody @Valid ChangePasswordDto changePasswordDto){
        return authService.changePassword(changePasswordDto);
    }

}
