package com.sagun.ecommerce_backend.dto;

public record VerifyOtpResponseDTO(
        String accessToken,
        String tokenType,
        long expiresInSeconds
) {
    public VerifyOtpResponseDTO(String accessToken, long expiresIn){
        this(accessToken,"Bearer",expiresIn);
    }
}
