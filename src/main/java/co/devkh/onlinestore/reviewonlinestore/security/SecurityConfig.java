package co.devkh.onlinestore.reviewonlinestore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                        "/api/v1/products/**").hasAuthority("product:read")
                .requestMatchers(HttpMethod.POST,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("product:write")
                .requestMatchers(HttpMethod.PUT,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("product:update")
                .requestMatchers(HttpMethod.DELETE,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("product:delete")

                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAuthority("user:profile")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("user:read")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("user:write")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("user:update")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("user:delete")

                .anyRequest().authenticated());

        // TODO : Use default login
      //  httpSecurity.formLogin(Customizer.withDefaults());

        // TODO : Configure HTTP Basic for Client Application. Example : Postman, Insomnia
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(token->token.disable());

        // TODO : Update API policy to STATELESS (Every communications is STATELESS)
        httpSecurity.sessionManagement(session->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // TODO Purpose when we configured already we need to return build this configured.
        return httpSecurity.build();
    }
}