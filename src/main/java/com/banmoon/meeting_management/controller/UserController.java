package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.service.AuthService;
import com.banmoon.meeting_management.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService firebaseAuthService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String idToken) {
        try {
            // 1️⃣ Firebase ID 토큰 검증
            FirebaseToken firebaseToken = firebaseAuthService.verifyIdToken(idToken);

            // 2️⃣ 유저 정보 가져오기
            String uid = firebaseToken.getUid();
            String email = firebaseToken.getEmail();
            String name = firebaseToken.getName();

            // 3️⃣ DB에서 사용자 조회 or 저장
            User user = userService.findOrCreateUser(uid, email, name);

            // 4️⃣ 로그인 성공 응답 반환
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
    }
}
