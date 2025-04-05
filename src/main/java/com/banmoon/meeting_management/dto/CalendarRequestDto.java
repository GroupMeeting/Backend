package com.banmoon.meeting_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CalendarRequestDto {
    private Long userId;  // 개인 일정이면 사용
    private Long clubId;  // 동아리 일정이면 사용
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
