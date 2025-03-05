package com.banmoon.meeting_management.service;


import com.banmoon.meeting_management.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    public User login(String idToken) {
        FirebaseToken firebaseToken = verifyIdToken(idToken); // ID 토큰 검증
        String uid = firebaseToken.getUid();
        String email = firebaseToken.getEmail();
        String displayName = firebaseToken.getName();

        return userService.findOrCreateUser(uid, email, displayName); // 유저 조회 or 생성
    }

    public FirebaseToken verifyIdToken(String idToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("토큰 검증 실패", e);
        }
    }


}
