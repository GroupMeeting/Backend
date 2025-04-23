package com.banmoon.meeting_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String title;
    private String description;
    private String date;
    private String startTime;
    private String endTime;
    private Long groupId;
    private Long meetingId;
}
