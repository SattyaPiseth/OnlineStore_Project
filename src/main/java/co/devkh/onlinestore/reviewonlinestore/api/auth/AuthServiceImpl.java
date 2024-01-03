package co.devkh.onlinestore.reviewonlinestore.api.auth;

import co.devkh.onlinestore.reviewonlinestore.api.auth.web.*;
import co.devkh.onlinestore.reviewonlinestore.api.mail.Mail;
import co.devkh.onlinestore.reviewonlinestore.api.mail.MailService;
import co.devkh.onlinestore.reviewonlinestore.api.user.User;
import co.devkh.onlinestore.reviewonlinestore.api.user.UserService;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.NewUserDto;
import co.devkh.onlinestore.reviewonlinestore.util.RandomUtil;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final MailService mailService;

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
    public void register(RegisterDto registerDto) throws MessagingException {
        NewUserDto newUserDto = authMapper.mapRegisterDtoToNewUserDto(registerDto);
        userService.createNewUser(newUserDto);

        String verifiedCode = RandomUtil.generateCode();

        // Store verifiedCode in database
        authRepository.updateVerifiedCode(registerDto.username(),verifiedCode);

        // Send verifiedCode via email
        Mail<String> verifiedMail = new Mail<>();
        verifiedMail.setSubject("Email Verification");
        verifiedMail.setSender(adminMail);
        verifiedMail.setReceiver(newUserDto.email());
        verifiedMail.setTemplate("auth/verify-mail");
        verifiedMail.setMetaData(verifiedCode);

        mailService.sendMail(verifiedMail);
    }

    @Transactional
    @Override
    public void verify(VerifyDto verifyDto) {

        User verifiedUser = authRepository.findByEmailAndVerifiedCodeAndIsDeletedFalse(verifyDto.email(),
                verifyDto.verifiedCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Verify email has been failed..!"));

        verifiedUser.setIsVerified(true);
        verifiedUser.setVerifiedCode(null);

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
