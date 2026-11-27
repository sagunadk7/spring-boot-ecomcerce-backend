package com.sagun.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyOtpRequestDto(
        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "OTP is required")
        @Pattern(regexp = "^\\d{6}$",message = "OTP must be a 6-digit number")
        String otp
) {}
