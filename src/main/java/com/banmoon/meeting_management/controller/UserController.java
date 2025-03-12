package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.domain.SocialLoginType;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.service.AuthService;
import com.banmoon.meeting_management.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

public class UserController {

    private final AuthService authService;  // ✅ 변수명 수정
    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService) { // 🔥 Lombok 없이 직접 주입
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String idToken) {
        try {
            FirebaseToken firebaseToken = authService.verifyIdToken(idToken);
            String uid = firebaseToken.getUid();
            String email = firebaseToken.getEmail();
            String name = firebaseToken.getName();
            String picture = (String) firebaseToken.getClaims().getOrDefault("picture", "");
            SocialLoginType socialLoginType = getSocialLoginType(firebaseToken.getIssuer());

            User user = userService.findOrCreateUser(uid, email, name, picture, socialLoginType);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
    }

    private SocialLoginType getSocialLoginType(String issuer) {
        if (issuer.contains("google")) return SocialLoginType.GOOGLE;
        else if (issuer.contains("apple")) return SocialLoginType.APPLE;
        return SocialLoginType.UNKNOWN;
    }
}
