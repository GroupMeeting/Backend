package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.dto.LoginRequestDto;
import com.banmoon.meeting_management.dto.SignupRequestDto;
import com.banmoon.meeting_management.dto.UserResponseDto;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        UserResponseDto response = userService.signup(requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        String token = userService.login(requestDto);
        return ResponseEntity.ok().body("Bearer " + token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
