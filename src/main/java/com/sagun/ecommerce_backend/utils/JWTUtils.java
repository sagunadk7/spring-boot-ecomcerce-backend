package com.sagun.ecommerce_backend.utils;

import com.sagun.ecommerce_backend.principal.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTUtils {

    private static int EXPIRATION_TIME = 1000*60*15;
    private static final String SECRET_KEY = "MY_KWY_ICHN_DFHAKJH9R98WYE8R+3274823@__EIFAE_IAM_GOING";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));


        public String generateToken(String username){
            return Jwts.builder()
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                    .signWith(key)
                    .subject(username)
                    .compact();
        }

        public Claims extractClaims(String token){
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

        public String extractUsername(String token){
            return extractClaims(token).getSubject();
        }

        public boolean isTokenExpired(String token){
            return extractClaims(token).getExpiration().before(new Date());
        }

        public boolean isValidToken(String token, UserPrincipal principal,String username){
            return principal.getUsername().equals(username) && !isTokenExpired(token);
        }

}
