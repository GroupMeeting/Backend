package com.banmoon.meeting_management.security;

import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String idToken = request.getHeader("Authorization");  // 헤더에서 ID 토큰 가져옴
        if (idToken != null && idToken.startsWith("Bearer ")) {
            idToken = idToken.substring(7);  // "Bearer " 제거
            try {
                FirebaseToken decodedToken = jwtProvider.verifyIdToken(idToken);

                // FirebaseToken에서 uid 가져오기
                String uid = decodedToken.getUid();

                // 인증 객체 생성 (현재는 인증 처리 생략, 필요 시 UserDetailsService 연동)
                SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthentication(uid));
            } catch (Exception e) {
                System.out.println("Invalid Firebase Token: " + e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
