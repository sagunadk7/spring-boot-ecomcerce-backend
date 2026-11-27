package com.sagun.ecommerce_backend.service;


import com.sagun.ecommerce_backend.customExceptions.UserNotFoundException;
import com.sagun.ecommerce_backend.dto.LoginCodeResponseDTO;
import com.sagun.ecommerce_backend.dto.VerifyOtpRequestDto;
import com.sagun.ecommerce_backend.dto.VerifyOtpResponseDTO;
import com.sagun.ecommerce_backend.entity.LoginCode;
import com.sagun.ecommerce_backend.entity.User;
import com.sagun.ecommerce_backend.repository.LoginCodeRepository;
import com.sagun.ecommerce_backend.repository.UserRepository;
import com.sagun.ecommerce_backend.utils.JWTUtils;
import com.sagun.ecommerce_backend.utils.LoginCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LoginCodeRepository loginCodeRepository;
    private final JWTUtils jwtUtils;

    @Transactional
    public LoginCodeResponseDTO getCode(String phoneNumber){
        if(phoneNumber == null || !phoneNumber.matches("\\d{10}")){
            throw new IllegalArgumentException("Phone number must be exactly 10 digits");
        }

        User user = userRepository.findByPhoneNumber(phoneNumber).orElseGet(
                () -> {
                    User newUser = new User();
                    newUser.setPhoneNumber(phoneNumber);
                    return userRepository.save(newUser);
                });
        LocalDateTime now = LocalDateTime.now();
        LoginCode loginCode = user.getLoginCode();

        if(loginCode != null && loginCode.getExpiresAt() != null && loginCode.getExpiresAt().isAfter(now)){
            long  millisLeft = Duration.between(now, loginCode.getExpiresAt()).toMillis();
            long remainingSeconds = (long) Math.ceil(millisLeft/1000.0);
            return new LoginCodeResponseDTO(
                    "An active verification code sent successfully",
                    loginCode.getCode(),
                    loginCode.getExpiresAt(),
                    remainingSeconds
            );
        }
        if(loginCode == null ){
           loginCode =  new LoginCode();
           loginCode.setUser(user);
           user.setLoginCode(loginCode);
        }

        String code = LoginCodeGenerator.generate();
        LocalDateTime expiresAt = now.plusMinutes(5);
        loginCode.setCode(code);
        loginCode.setExpiresAt(expiresAt);
        user.setLoginCode(loginCode);

        return new LoginCodeResponseDTO(
                "Verification code sent successfully",
                code,
                expiresAt,
                300
        );
    }

    public VerifyOtpResponseDTO verifyToken(VerifyOtpRequestDto requestDto){
        String phoneNumber = requestDto.phoneNumber();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User does not exists"));
        LoginCode loginCode = user.getLoginCode();
        if(loginCode == null ){
            try {
                throw new BadRequestException("OTP is null");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        if(!phoneNumber.equals(user.getPhoneNumber()) || !requestDto.otp().equals(loginCode.getCode())){
            throw new RuntimeException("phone number or otp does not matches ");
        }

        if(loginCode.getExpiresAt().isBefore(LocalDateTime.now())){
            user.setLoginCode(null);
            userRepository.save(user);
            throw new RuntimeException("OTP expired");
        }

        user.setLoginCode(null);
        userRepository.save(user);
        String accessToken = jwtUtils.generateToken(user.getPhoneNumber());
        return new VerifyOtpResponseDTO(accessToken,Duration.ofMinutes(15).toSeconds());


    }




}
