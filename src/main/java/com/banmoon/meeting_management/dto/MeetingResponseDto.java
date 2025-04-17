// MeetingResponseDto.java
package com.banmoon.meeting_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MeetingResponseDto {
    private Long id;
    private String title;
    private String meetingDate;
    private String startTime;
    private String endTime;
    private Long creatorId;
    private Long locationId;
    private String locationName;
    private Long fee;
}
