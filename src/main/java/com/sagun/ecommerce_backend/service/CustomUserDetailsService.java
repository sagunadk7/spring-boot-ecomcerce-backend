package com.sagun.ecommerce_backend.service;

import com.sagun.ecommerce_backend.entity.User;
import com.sagun.ecommerce_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
    }
}
