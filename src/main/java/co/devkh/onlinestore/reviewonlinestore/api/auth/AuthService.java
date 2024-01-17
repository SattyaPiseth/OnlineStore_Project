package co.devkh.onlinestore.reviewonlinestore.api.auth;

import co.devkh.onlinestore.reviewonlinestore.api.auth.web.Dto.*;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    /**
     * Refresh token is used to get new access token
     * @param refreshTokenDto of Request data from client
     * @return new access token
     */
    AuthDto refreshToken(RefreshTokenDto refreshTokenDto);

    /**
     * Register new user
     * @param registerDto of Request data from client
     * @return message for client
     * @throws MessagingException if email is not valid
     */
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

    /**
     * Verify user by token
     * @param token of Request data from client
     */
    void verifyUser(String token);

    /**
     * Forgot password service
     * @param forgotPasswordDto of Request data from client
     * @return message for client
     * @throws MessagingException if email is not valid
     */
    ResponseEntity<?> forgotPassword(ForgotPasswordDto forgotPasswordDto) throws MessagingException;

    /**
     * Reset password service
     * @param resetPasswordDto of Request data from client
     */
    void resetPassword(ResetPasswordDto resetPasswordDto);

    /**
     * Verify reset token
     * @param token of Request data from client
     * @return true if token is valid
     */
    boolean verifyResetToken(String token);

    /**
     * Change password service
     * @param changePasswordDto of Request data from client
     * @return message for client
     */
    ResponseEntity<Object> changePassword(ChangePasswordDto changePasswordDto);

}
