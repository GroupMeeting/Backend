package com.banmoon.meeting_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String name;
    private String birth;
    private String phone;
    private String address;
    private String profileImage;
}
