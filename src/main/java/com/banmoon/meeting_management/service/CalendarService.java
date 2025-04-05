package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.dto.CalendarRequestDto;
import com.banmoon.meeting_management.dto.CalendarResponseDto;
import com.banmoon.meeting_management.entity.Calendar;
import com.banmoon.meeting_management.entity.Club;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.CalendarRepository;
import com.banmoon.meeting_management.repository.ClubRepository;
import com.banmoon.meeting_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto) {
        Calendar calendar = new Calendar();
        calendar.setTitle(requestDto.getTitle());
        calendar.setDescription(requestDto.getDescription());
        calendar.setDate(requestDto.getDate());
        calendar.setStartTime(requestDto.getStartTime());
        calendar.setEndTime(requestDto.getEndTime());

        if (requestDto.getUserId() != null) {
            User user = userRepository.findById(requestDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("사용자 ID가 유효하지 않습니다."));
            calendar.setUser(user);
        }

        if (requestDto.getClubId() != null) {
            Club club = clubRepository.findById(requestDto.getClubId())
                    .orElseThrow(() -> new IllegalArgumentException("클럽 ID가 유효하지 않습니다."));
            calendar.setClub(club);
        }

        Calendar saved = calendarRepository.save(calendar);
        return new CalendarResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getDate(),
                saved.getStartTime(),
                saved.getEndTime()
        );
    }

    public CalendarResponseDto getCalendar(Long id) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        return new CalendarResponseDto(
                calendar.getId(),
                calendar.getTitle(),
                calendar.getDescription(),
                calendar.getDate(),
                calendar.getStartTime(),
                calendar.getEndTime()
        );
    }
}
