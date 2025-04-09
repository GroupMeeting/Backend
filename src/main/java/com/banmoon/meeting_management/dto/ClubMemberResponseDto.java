package com.banmoon.meeting_management.dto;

import com.banmoon.meeting_management.entity.ClubRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubMemberResponseDto {
    private Long userId;
    private String name;
    private String profileImage;
    private ClubRole role;
}
