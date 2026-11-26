package com.sagun.ecommerce_backend.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record LoginCodeResponseDTO (
  String message,
  String code,
  LocalDateTime expiresAt,
  long expiresInSeconds
){}
