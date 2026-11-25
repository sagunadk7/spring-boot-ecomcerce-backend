package com.sagun.ecommerce_backend.service;

import com.sagun.ecommerce_backend.repository.LoginCodeRepository;
import com.sagun.ecommerce_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LoginCodeRepository loginCodeRepository;



}
