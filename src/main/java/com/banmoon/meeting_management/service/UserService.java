package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.domain.SocialLoginType;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User findOrCreateUser(String uid, String email, String displayName, String photoUrl, SocialLoginType socialLoginType) {
        return userRepository.findByUid(uid)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .uid(uid)
                            .email(email)
                            .name(displayName)
                            .profileImage(photoUrl)
                            .socialLoginType(socialLoginType)
                            .build();
                    return userRepository.save(newUser);
                });
    }
}
