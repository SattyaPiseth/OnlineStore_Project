package co.devkh.onlinestore.reviewonlinestore.api.auth;

import co.devkh.onlinestore.reviewonlinestore.api.auth.web.*;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthDto refreshToken(RefreshTokenDto refreshTokenDto);
    String register(RegisterDto registerDto) throws MessagingException;


    /**
     * verify by generate code 6 digits
     * @param verifyDto of Request data from client
     */
    void verify(VerifyDto verifyDto);

    /**
     * Just before, we used to select Authorization data on postman
     * if we complete fill information user,password. But now if we want to log in
     * access token for your own. In Spring, It provided class for operating-automatic
     * is calling Method (loadUserByUsername). So we can trigger to calling Method (loadUserByUsername)
     * via [UsernamePasswordAuthenticationToken]. The behaviors of this class is authenticated user via
     * java code. when we authenticate via this class we need to trigger calling Method
     * (loadUserByUsername).
     * @param loginDto of Request data from client
     */
    AuthDto login(LoginDto loginDto);

    void verifyUser(String token);

    void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws MessagingException;

    void resetPassword(ResetPasswordDto resetPasswordDto);

    boolean verifyResetToken(String token);

    // change password service
    ResponseEntity<Object> changePassword(ChangePasswordDto changePasswordDto);
}
