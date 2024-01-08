package co.devkh.onlinestore.reviewonlinestore.security;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.User;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User securityUser = userRepository.findByUsernameAndIsDeletedFalseAndIsVerifiedTrue(username)
                .orElseThrow(() ->{
                    log.error("Username has not been found..!");
                    return new UsernameNotFoundException("Username has not been found..!");
                });

        log.info("Security user = {}",securityUser.getUsername());
        log.info("Security user = {}",securityUser.getEmail());

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(securityUser);

        log.info("Security user = {}",customUserDetails.getAuthorities());

        return customUserDetails;
    }
}
