package com.sagun.ecommerce_backend.filter;

import com.sagun.ecommerce_backend.principal.UserPrincipal;
import com.sagun.ecommerce_backend.service.CustomUserDetailsService;
import com.sagun.ecommerce_backend.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.regionMatches(true,0,"Bearer ",0,7)){
            filterChain.doFilter(request,response);
            return;
        }
        try {
            String token = authHeader.substring(7);
            String username = jwtUtils.extractUsername(token);
            if( username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserPrincipal userPrincipal = userDetailsService.loadUserByUsername(username);
                if(jwtUtils.isValidToken(token,userPrincipal,username)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception exception){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false,\"message\":\"invalid or expired token.\"}");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
