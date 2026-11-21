package com.sagun.ecommerce_backend.principal;

import com.sagun.ecommerce_backend.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final String username;
    private final String passwordHash;
    @lombok.Getter
    private final List<SimpleGrantedAuthority> roles;


    private UserPrincipal (String username, String passwordHash, List<SimpleGrantedAuthority> roles){
        this.username = username;
        this.passwordHash = passwordHash;
        this.roles = roles;
    }

    public static UserPrincipal from(User user){
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList();
        return new UserPrincipal(user.getUsername(),user.getPasswordHash(),authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
