package com.banmoon.meeting_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String name;
    private String birth;
    private String phone;
    private String address;
    private String profileImage;


    public SignupRequestDto(String username, String password, String name, String birth, String phone, String address, String profileImage) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.profileImage = profileImage;
    }
}
