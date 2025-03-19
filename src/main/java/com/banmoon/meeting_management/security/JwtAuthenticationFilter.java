package com.banmoon.meeting_management.security;

import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider; // final 유지

    // ✅ 명시적 생성자 추가
    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String idToken = request.getHeader("Authorization");
        if (idToken != null && idToken.startsWith("Bearer ")) {
            idToken = idToken.substring(7);
            try {
                FirebaseToken decodedToken = jwtProvider.verifyIdToken(idToken);
                String uid = decodedToken.getUid();
                SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthentication(uid));
            } catch (Exception e) {
                System.out.println("Invalid Firebase Token: " + e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
