package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.dto.CalendarRequestDto;
import com.banmoon.meeting_management.dto.CalendarResponseDto;
import com.banmoon.meeting_management.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createCalendar(@RequestBody CalendarRequestDto requestDto) {
        CalendarResponseDto responseDto = calendarService.createCalendar(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarResponseDto> getCalendar(@PathVariable Long id) {
        CalendarResponseDto responseDto = calendarService.getCalendar(id);
        return ResponseEntity.ok(responseDto);
    }
}
