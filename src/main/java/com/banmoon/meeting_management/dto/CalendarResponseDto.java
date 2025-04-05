package com.banmoon.meeting_management.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
