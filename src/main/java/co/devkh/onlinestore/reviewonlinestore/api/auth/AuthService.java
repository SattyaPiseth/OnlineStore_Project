package co.devkh.onlinestore.reviewonlinestore.api.auth;

import co.devkh.onlinestore.reviewonlinestore.api.auth.web.LoginDto;
import co.devkh.onlinestore.reviewonlinestore.api.auth.web.RegisterDto;
import co.devkh.onlinestore.reviewonlinestore.api.auth.web.VerifyDto;
import jakarta.mail.MessagingException;

public interface AuthService {
    void register(RegisterDto registerDto) throws MessagingException;
    void verify(VerifyDto verifyDto);

    void login(LoginDto loginDto);
}
