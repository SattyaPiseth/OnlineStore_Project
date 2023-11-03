package co.devkh.onlinestore.reviewonlinestore.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    //TODO    We have two methods for customized create user for Spring Security.
//      1. In Memory User : user is created if application started. and application stop all user we created is deleted.
//      2. DAO User : this method is related user we created and store this data in database.

//     TODO Let's start create user (In Memory User)
//     TODO Algorithm 1
//     TODO Step 1: We must create a Bean from InMemoryUserDetailsManager.
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        //TODO Step 2 : create object from this class for injected to create user.
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        //TODO Step 3 : let's start create user and injected to [manager] that is object of this class.
//        // And then we need to know .build() return to UserDetails we must create UserDetails for receive it.
//
//        // Create admin
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("12345"))
//                .roles("ADMIN")
//                .build();
//
//        // Create staff
//        UserDetails staff = User.withUsername("staff")
//                .password(passwordEncoder.encode("12345"))
//                .roles("STAFF")
//                .build();
//
//        // Create customer
//        UserDetails customer = User.withUsername("customer")
//                .password(passwordEncoder.encode("12345"))
//                .roles("CUSTOMER")
//                .build();
//
//        //TODO Add user into in-memory user manager (injected all user to [manager])
//        manager.createUser(admin);
//        manager.createUser(staff);
//        manager.createUser(customer);
//
//        return manager;
//    }


    // TODO: We must create Bean from JwtAuthenticationProvider for authenticated
    //  If we have only token for authentication. Because we check JwtAuthenticationProvider need parameter [JwtDecoder]
    // Step 1 : [JwtDecoder] for refreshToken = [jwtRefreshTokenDecoder()].
    // Step 2 : [jwtRefreshTokenDecoder()] of refreshToken need [rsaKey] form refreshToken.
    // Step 3 : [rsaKey] form refreshToken need [KeyPair] form refreshToken.

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider() throws JOSEException {
        JwtAuthenticationProvider provider =
                new JwtAuthenticationProvider(
                        jwtRefreshTokenDecoder(
                                refreshTokenRsaKey(refreshTokenKeyPair())));
        return provider;
    }

    // TODO Let's start create user (In DAO User)
    // TODO Algorithm 1.2
    // TODO Step 1.2: We must create a Bean from DaoAuthenticationProvider.
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        //TODO Step 2.2 : create object from this class for injected to create user.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //TODO Step 3 : let's start create user and injected to [provider] that is object of this class.
        // set User form UserDetailsService and set Password from PasswordEncoder.
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


// TODO Algorithm 2
//  We need to configured request-route (we need to authorized security all of requested API endpoints)
//  Step 1 : We must create a Bean from SecurityFilterChain for authorized security.
//  [if authorized passed , request has meet dispatcher servlet and handle to controller]
// TODO  Step 2 : If we want to configure we need to injected HttpSecurity for configurer.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // TODO: What you want to customize
        httpSecurity.authorizeHttpRequests(auth-> auth
                .requestMatchers("/api/v1/auth/**","/api/v1/files/**").permitAll()
                .requestMatchers(HttpMethod.GET,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("SCOPE_product:read")
                .requestMatchers(HttpMethod.POST,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("SCOPE_product:write")
                .requestMatchers(HttpMethod.PUT,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("SCOPE_product:update")
                .requestMatchers(HttpMethod.DELETE,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("SCOPE_product:delete")

                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAuthority("SCOPE_user:profile")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("SCOPE_user:read")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("SCOPE_user:write")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("SCOPE_user:update")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("SCOPE_user:delete")

                .anyRequest().authenticated());

        // TODO : Use default login
      //  httpSecurity.formLogin(Customizer.withDefaults());

        // TODO : Configure JWT | OAuth2 Resource Server.
        httpSecurity.oauth2ResourceServer(oauth2->oauth2
                .jwt(Customizer.withDefaults()));

        httpSecurity.csrf(token->token.disable());

        // TODO : Update API policy to STATELESS (Every communications is STATELESS)
        httpSecurity.sessionManagement(session->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // TODO Purpose when we configured already we need to return build this configured.
        return httpSecurity.build();
    }
    @Bean
    @Primary
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    @Primary
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
    @Bean
    @Primary
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    @Bean
    @Primary
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        };
    }
    @Bean
    @Primary
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }


    /*=> Start Implemented Refresh Token <=*/
    @Bean("refreshTokenKeyPair")
    public KeyPair refreshTokenKeyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean("refreshTokenRsaKey")
    public RSAKey refreshTokenRsaKey(@Qualifier("refreshTokenKeyPair") KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
    @Bean("jwtRefreshTokenDecoder")
    JwtDecoder jwtRefreshTokenDecoder(@Qualifier("refreshTokenRsaKey") RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    @Bean("jwtRefreshTokenEncoder")
    JwtEncoder jwtRefreshTokenEncoder(@Qualifier("refreshTokenJwkSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean("refreshTokenJwkSource")
    public JWKSource<SecurityContext> refreshTokenJwkSource(@Qualifier("refreshTokenRsaKey") RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        };
    }
}