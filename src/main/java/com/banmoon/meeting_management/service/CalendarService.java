package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.dto.CalendarRequestDto;
import com.banmoon.meeting_management.dto.CalendarResponseDto;
import com.banmoon.meeting_management.entity.Calendar;
import com.banmoon.meeting_management.entity.Club;
import com.banmoon.meeting_management.entity.Meeting;
import com.banmoon.meeting_management.repository.CalendarRepository;
import com.banmoon.meeting_management.repository.ClubRepository;
import com.banmoon.meeting_management.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final ClubRepository clubRepository;
    private final MeetingRepository meetingRepository;

    public CalendarResponseDto createCalendar(CalendarRequestDto dto) {
        Calendar calendar = new Calendar();
        calendar.setTitle(dto.getTitle());
        calendar.setDescription(dto.getDescription());
        calendar.setDate(dto.getDate());
        calendar.setStartTime(dto.getStartTime());
        calendar.setEndTime(dto.getEndTime());

        if (dto.getGroupId() != null) {
            Club club = clubRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 그룹을 찾을 수 없습니다."));
            calendar.setClub(club);
        }

        if (dto.getMeetingId() != null) {
            Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 약속을 찾을 수 없습니다."));
            calendar.setMeeting(meeting);
        }

        Calendar saved = calendarRepository.save(calendar);
        return toResponseDto(saved);
    }

    public CalendarResponseDto getCalendar(Long id) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        return toResponseDto(calendar);
    }

    public void updateCalendar(Long calendarId, CalendarRequestDto dto) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        calendar.setTitle(dto.getTitle());
        calendar.setDescription(dto.getDescription());
        calendar.setDate(dto.getDate());
        calendar.setStartTime(dto.getStartTime());
        calendar.setEndTime(dto.getEndTime());

        calendarRepository.save(calendar);
    }

    public void deleteCalendar(Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        calendarRepository.delete(calendar);
    }

    public CalendarResponseDto toResponseDto(Calendar calendar) {
        return new CalendarResponseDto(
                calendar.getId(),
                calendar.getTitle(),
                calendar.getDescription(),
                calendar.getDate(),
                calendar.getStartTime(),
                calendar.getEndTime(),
                calendar.getClub() != null ? calendar.getClub().getId() : null,
                calendar.getMeeting() != null ? calendar.getMeeting().getId() : null
        );
    }
}
