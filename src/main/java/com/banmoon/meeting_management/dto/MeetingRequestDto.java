package com.banmoon.meeting_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingRequestDto {
    private String title;
    private String meetingDate;
    private String startTime;
    private String endTime;
    private Long locationId;
    private Long creatorId;
    private Long fee;
}

