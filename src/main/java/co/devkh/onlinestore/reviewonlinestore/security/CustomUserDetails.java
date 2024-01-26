package co.devkh.onlinestore.reviewonlinestore.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {
//        private User user;
      private String username;
      private String password;
      private Collection<? extends GrantedAuthority> authorities;
      private boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//
//            role.getAuthorities().forEach(authority -> {
//                authorities.add(new SimpleGrantedAuthority(authority.getName()));
//            });
//        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled; //user.getIsVerified();
    }
}
