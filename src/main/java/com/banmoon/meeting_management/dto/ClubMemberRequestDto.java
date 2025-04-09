package com.banmoon.meeting_management.dto;

import com.banmoon.meeting_management.entity.ClubRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubMemberRequestDto {
    private Long userId;
    private ClubRole role;
}
