package com.banmoon.meeting_management.service;


import com.banmoon.meeting_management.domain.SocialLoginType;
import com.banmoon.meeting_management.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) { // 🔥 Lombok 없이 직접 주입
        this.userService = userService;
    }

    public User login(String idToken) {
        FirebaseToken firebaseToken = verifyIdToken(idToken); // ID 토큰 검증
        String uid = firebaseToken.getUid();
        String email = firebaseToken.getEmail();
        String displayName = firebaseToken.getName();
        String photoUrl = firebaseToken.getPicture();
        String issuer = firebaseToken.getIssuer(); // "https://accounts.google.com" 또는 "https://appleid.apple.com"

        SocialLoginType socialLoginType = getSocialLoginType(issuer);

        return userService.findOrCreateUser(uid, email, displayName, photoUrl, socialLoginType);
    }

    public FirebaseToken verifyIdToken(String idToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("토큰 검증 실패", e);
        }
    }

    public SocialLoginType getSocialLoginType(String issuer) {
        if (issuer.contains("google")) {
            return SocialLoginType.GOOGLE;
        } else if (issuer.contains("apple")) {
            return SocialLoginType.APPLE;
        } else {
            throw new IllegalArgumentException("지원하지 않는 소셜 로그인 타입: " + issuer);
        }
    }

}
