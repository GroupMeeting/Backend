package com.banmoon.meeting_management.dto;

import com.banmoon.meeting_management.entity.User;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String profileImageUrl;
    private final String socialLoginType;
    private final String jwt;

    public LoginResponse(User user, String jwt) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImage();
        this.socialLoginType = user.getSocialLoginType().name();
        this.jwt = jwt;
    }
}
