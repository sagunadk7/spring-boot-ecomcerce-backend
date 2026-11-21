package com.sagun.ecommerce_backend.service;

import com.sagun.ecommerce_backend.entity.User;
import com.sagun.ecommerce_backend.principal.UserPrincipal;
import com.sagun.ecommerce_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserPrincipal loadUserByUsername( String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        return UserPrincipal.from(user);
    }
}
