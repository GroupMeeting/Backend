package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.dto.CalendarRequestDto;
import com.banmoon.meeting_management.dto.CalendarResponseDto;
import com.banmoon.meeting_management.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createCalendar(@RequestBody CalendarRequestDto dto) {
        return ResponseEntity.ok(calendarService.createCalendar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarResponseDto> getCalendar(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getCalendar(id));
    }

    @PatchMapping("/{calendarId}")
    public ResponseEntity<?> updateCalendar(@PathVariable Long calendarId,
                                            @RequestBody CalendarRequestDto dto) {
        calendarService.updateCalendar(calendarId, dto);
        return ResponseEntity.ok(Map.of("message", "일정이 수정되었습니다."));
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<?> deleteCalendar(@PathVariable Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return ResponseEntity.ok(Map.of("message", "일정이 삭제되었습니다."));
    }

}
