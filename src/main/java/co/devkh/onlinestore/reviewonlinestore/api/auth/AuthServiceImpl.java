package co.devkh.onlinestore.reviewonlinestore.api.auth;

import co.devkh.onlinestore.reviewonlinestore.api.auth.web.*;
import co.devkh.onlinestore.reviewonlinestore.api.mail.Mail;
import co.devkh.onlinestore.reviewonlinestore.api.mail.MailService;
import co.devkh.onlinestore.reviewonlinestore.api.user.User;
import co.devkh.onlinestore.reviewonlinestore.api.user.UserService;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.NewUserDto;
import co.devkh.onlinestore.reviewonlinestore.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder jwtEncoder;

    private JwtEncoder jwtRefreshTokenEncoder;
    @Autowired
    public void setJwtRefreshTokenEncoder(@Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder) {
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
    }

    @Value("${spring.mail.username}")
    private String adminMail;

    @Value("${app.base-uri}")
    private String appBaseUri;

    @Override
    public AuthDto refreshToken(RefreshTokenDto refreshTokenDto) {
  // If we have only token for authentication we need to use class BearerTokenAuthenticationToken for authenticated;
     Authentication auth = new BearerTokenAuthenticationToken(refreshTokenDto.refreshToken());
     auth = jwtAuthenticationProvider.authenticate(auth);
     // this we have only token for authentication. if authenticated passed. auth have user details.
        // but we need downcast to return type jwt and access to user details
        Jwt jwt = (Jwt) auth.getPrincipal();
        log.info("Name: {}",jwt.getId());
        log.info("Subject: {}",jwt.getSubject());

//        String scope = auth.getAuthorities().stream()
//                .map(grantedAuthority -> grantedAuthority.getAuthority())
//                .collect(Collectors.joining(" "));

        return AuthDto.builder()
                .type("Bearer")
                .accessToken(generateAccessToken(GenerateTokenDto.builder()
                        .auth(jwt.getId())
                        .expiration(Instant.now().plus(1,ChronoUnit.HOURS))
                        .scope(jwt.getClaimAsString("scope"))
                        .build()))
                .refreshToken(checkDurationRefreshToken(GenerateTokenDto.builder()
                        .auth(jwt.getId())
                        .expiration(Instant.now().plus(30,ChronoUnit.DAYS))
                        .scope(jwt.getClaimAsString("scope"))
                        .duration(Duration.between(Instant.now(),jwt.getExpiresAt()))
                        .checkDurationValue(7)
                        .previousToken(refreshTokenDto.refreshToken())
                        .build()))
                .build();
    }

    @Transactional
    @Override
    public String register(RegisterDto registerDto) throws MessagingException {

        // Map registration data and create user
        NewUserDto newUserDto = authMapper.mapRegisterDtoToNewUserDto(registerDto);
        userService.createNewUser(newUserDto);

        // Generate verification code and token
        String verificationCode = RandomUtil.generateCode();
        String verificationToken = UUID.randomUUID().toString();

        // Store verification details in the database
        authRepository.updateVerifiedCode(registerDto.username(), verificationCode);
        authRepository.updateIsVerifiedToken(registerDto.username(), verificationToken);

        // Prepare and send verification emails
        String verificationLink = appBaseUri + "api/v1/auth/verify?token=" + verificationToken;

        // First email with verification code
        Mail<String> verificationCodeMail = createVerificationEmail(
                newUserDto.email(), verificationCode, "auth/verify-mail");
        mailService.sendMail(verificationCodeMail);

        // Second email with verification link
        Mail<String> verificationLinkMail = createVerificationEmail(
                newUserDto.email(), verificationLink, "auth/verify-token-mail");
        mailService.sendMail(verificationLinkMail);

        return verificationLink;
    }

    private Mail<String> createVerificationEmail(String recipient, String metaData, String template) {
        Mail<String> mail = new Mail<>();
        mail.setSubject("Email Verification");
        mail.setSender(adminMail);
        mail.setReceiver(recipient);
        mail.setTemplate(template);
        mail.setMetaData(metaData);
        return mail;
    }


    @Transactional
    @Override
    public void verify(VerifyDto verifyDto) {

        // Find the user with matching email and verification code, ensuring they are not deleted
        User verifiedUser = authRepository.findByEmailAndVerifiedCodeAndIsDeletedFalse(
                        verifyDto.email(), verifyDto.verifiedCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid verification code"));

        // Mark the user as verified and clear sensitive fields
        verifiedUser.setIsVerified(true); // Assuming a boolean field for verification status
        verifiedUser.setVerifiedCode(null);
        verifiedUser.setVerifiedToken(null);

        // Save the updated user details
        authRepository.save(verifiedUser);
    }


    @Override
    public AuthDto login(LoginDto loginDto) {
    // If we have username and password we have to use class UsernamePasswordAuthenticationToken for authenticate;
    Authentication auth =  new UsernamePasswordAuthenticationToken(loginDto.username(),loginDto.password());
    // if authenticate passed. auth have UserDetails about credentials, else auth = null
    auth = daoAuthenticationProvider.authenticate(auth);

    log.info("AUTH = {}",auth.getName());
    log.info("AUTH = {}",auth.getAuthorities());
        String scope = auth.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));

        return AuthDto.builder()
                .type("Bearer")
                .accessToken(generateAccessToken(GenerateTokenDto.builder()
                        .auth(auth.getName())
                        .expiration(Instant.now().plus(1,ChronoUnit.HOURS))
                        .scope(scope)
                        .build()))
                .refreshToken(generateRefreshToken(GenerateTokenDto.builder()
                        .auth(auth.getName())
                        .expiration(Instant.now().plus(30,ChronoUnit.DAYS))
                        .scope(scope)
                        .build()))
                .build();
    }

    @Override
    public void verifyUser(String token) {

        // Find the user with the matching verification token, ensuring they are not deleted
        User user = authRepository.findByVerifiedTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid verification token"));

        // Verify the user only if they haven't been verified yet
        if (!user.getIsVerified()) {
            user.setIsVerified(true);  // Assuming a boolean field for verification status
            user.setVerifiedCode(null);
            user.setVerifiedToken(null);
            authRepository.save(user);
        }

    }

    @Transactional
    @Override
    public void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws MessagingException {

        // Find the user with the matching email, ensuring they are not deleted
        User user = authRepository.findByEmailAndIsDeletedFalse(forgotPasswordDto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email"));

        // Generate a password reset token and store it
        String passwordResetToken = UUID.randomUUID().toString();
        authRepository.updateIsVerifiedToken(user.getUsername(), passwordResetToken);

        // Prepare the password reset email
        String passwordResetLink = appBaseUri + "api/v1/auth/reset-password?token=" + passwordResetToken;
        Mail<String> passwordResetMail = createPasswordResetEmail(user.getEmail(), passwordResetLink);

        // Send the email
        mailService.sendMail(passwordResetMail);
        log.info("Password Reset Mail Sent: {}", passwordResetMail);

    }

    private Mail<String> createPasswordResetEmail(String recipient, String resetLink) {
        Mail<String> mail = new Mail<>();
        mail.setSubject("Reset Password");
        mail.setSender(adminMail);
        mail.setReceiver(recipient);
        mail.setTemplate("auth/forgot-password-mail");
        mail.setMetaData(resetLink);
        return mail;
    }


    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {

        // Find the user by email and retrieve their password reset token
        String passwordResetToken = authRepository.findByEmailAndIsDeletedFalse(resetPasswordDto.email())
                .map(User::getVerifiedToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or token"));

        // Find the user with the matching password reset token
        User user = authRepository.findByVerifiedTokenAndIsDeletedFalse(passwordResetToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password reset token"));

        // Update the password and clear the reset token
        user.setPassword(passwordEncoder.encode(resetPasswordDto.password()));
        user.setVerifiedToken(null);
        authRepository.save(user);
    }

    @Override
    public boolean verifyResetToken(String token) {
        return authRepository.existsByVerifiedTokenAndIsDeletedFalse(token);
    }


    private String generateAccessToken(GenerateTokenDto generateTokenDto){
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(generateTokenDto.auth())
                .issuer("Public")
                .issuedAt(Instant.now())
                .expiresAt(generateTokenDto.expiration())
                .subject("Access Token")
                .audience(List.of("Public Client"))
                .claim("scope",generateTokenDto.scope())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

    }
    private String generateRefreshToken(GenerateTokenDto generateTokenDto){
        JwtClaimsSet jwtRefreshTokenClaimsSet = JwtClaimsSet.builder()
                .id(generateTokenDto.auth())
                .issuer("Public")
                .issuedAt(Instant.now())
                .expiresAt(generateTokenDto.expiration())
                .subject("Refresh Token")
                .audience(List.of("Public Client"))
                .claim("scope",generateTokenDto.scope())
                .build();

        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(jwtRefreshTokenClaimsSet)).getTokenValue();
    }

    private String checkDurationRefreshToken(GenerateTokenDto generateTokenDto){
        log.info("Duration : {}",generateTokenDto.duration().toDays());
        if (generateTokenDto.duration().toDays() < generateTokenDto.checkDurationValue()){
            JwtClaimsSet jwtRefreshTokenClaimsSet = JwtClaimsSet.builder()
                    .id(generateTokenDto.auth())
                    .issuer("Public")
                    .issuedAt(Instant.now())
                    .expiresAt(generateTokenDto.expiration())
                    .subject("Refresh Token")
                    .audience(List.of("Public Client"))
                    .claim("scope",generateTokenDto.scope())
                    .build();
            return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(jwtRefreshTokenClaimsSet)).getTokenValue();
        }
        return generateTokenDto.previousToken();
    }
}
