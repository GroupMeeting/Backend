package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.dto.SignupRequestDto;
import com.banmoon.meeting_management.dto.LoginRequestDto;
import com.banmoon.meeting_management.dto.UserResponseDto;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.UserRepository;
import com.banmoon.meeting_management.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto signup(SignupRequestDto dto) {
        // 중복된 사용자명 검사
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 사용자 생성
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
        user.setName(dto.getName());
        user.setBirth(dto.getBirth());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setProfileImage(dto.getProfileImage());  // 선택사항

        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getName(),
                savedUser.getBirth(),
                savedUser.getPhone(),
                savedUser.getAddress(),
                savedUser.getProfileImage()
        );
    }

    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 -> JWT 토큰 발급
        return jwtUtil.createToken(user.getUsername());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getBirth(),
                user.getPhone(),
                user.getAddress(),
                user.getProfileImage()
        );
    }
}
