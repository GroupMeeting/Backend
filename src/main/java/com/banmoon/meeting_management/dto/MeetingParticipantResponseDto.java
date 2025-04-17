package com.banmoon.meeting_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MeetingParticipantResponseDto {
    private Long userId;
    private String userName;
    private String profileImage;
}
