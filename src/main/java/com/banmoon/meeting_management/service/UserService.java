package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreateUser(String uid, String email, String displayName) {
        return userRepository.findByUid(uid)
                .orElseGet(() -> createUser(uid, email, displayName));
    }

    private User createUser(String uid, String email, String displayName) {
        User newUser = User.builder()
                .uid(uid)
                .email(email)
                .displayName(displayName)
                .build();
        return userRepository.save(newUser);
    }
}
