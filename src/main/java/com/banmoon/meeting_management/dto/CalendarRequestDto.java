package com.banmoon.meeting_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarRequestDto {
    private String title;
    private String description;
    private String date;
    private String startTime;
    private String endTime;
    private Long groupId;    // null 허용
    private Long meetingId;  // null 허용
}
