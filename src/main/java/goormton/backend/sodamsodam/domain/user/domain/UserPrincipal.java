package goormton.backend.sodamsodam.domain.user.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {

    private User user;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public UserPrincipal(User user) {
        this.user = user;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    public UserPrincipal(User user,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attributes) {
        this.user = user;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public UserPrincipal(User user,
                         Map<String, Object> attributes) {
        this.user = user;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return null;
    }

    // 아이디에 해당하는 username을 email로 사용
    @Override
    public String getUsername() {
        return String.valueOf(user.getEmail());
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
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
        return true;
    }
}
