package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.dto.SignupRequestDto;
import com.banmoon.meeting_management.dto.LoginRequestDto;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.UserRepository;
import com.banmoon.meeting_management.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtProvider;

    public User signup(SignupRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(
                requestDto.getUsername(),
                encodedPassword,
                requestDto.getName(),
                requestDto.getBirth(),
                requestDto.getPhone(),
                requestDto.getAddress()
        );
        return userRepository.save(user);
    }

    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(user.getUsername());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
