package com.sagun.ecommerce_backend.controller;

import com.sagun.ecommerce_backend.dto.LoginCodeResponseDTO;
import com.sagun.ecommerce_backend.dto.VerifyOtpRequestDto;
import com.sagun.ecommerce_backend.dto.VerifyOtpResponseDTO;
import com.sagun.ecommerce_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/get-code")
    public LoginCodeResponseDTO getCode(@RequestBody String phoneNumber){
        System.out.println("I am here");
        return service.getCode(phoneNumber);
    }

    @PostMapping("/verify-otp")
    public VerifyOtpResponseDTO verifyOtp(@RequestBody VerifyOtpRequestDto requestDto){
        return service.verifyToken(requestDto);
    }

}
