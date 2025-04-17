package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.dto.MeetingParticipantRequestDto;
import com.banmoon.meeting_management.dto.MeetingRequestDto;
import com.banmoon.meeting_management.dto.MeetingResponseDto;
import com.banmoon.meeting_management.dto.UserResponseDto;
import com.banmoon.meeting_management.entity.Location;
import com.banmoon.meeting_management.entity.Meeting;
import com.banmoon.meeting_management.entity.MeetingParticipant;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.LocationRepository;
import com.banmoon.meeting_management.repository.MeetingParticipantRepository;
import com.banmoon.meeting_management.repository.MeetingRepository;
import com.banmoon.meeting_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;

    public MeetingResponseDto createMeeting(MeetingRequestDto dto) {
        // 생성자 사용자 정보 가져오기
        User user = userRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // locationId → Location 객체로 변환
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("장소를 찾을 수 없습니다."));

        // Meeting 엔티티 생성
        Meeting meeting = new Meeting();
        meeting.setTitle(dto.getTitle());
        meeting.setMeetingDate(dto.getMeetingDate());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());
        meeting.setCreator(user);
        meeting.setLocation(location);
        meeting.setFee(dto.getFee());


        Meeting saved = meetingRepository.save(meeting);

// 응답 생성 시
        return new MeetingResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getMeetingDate(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getCreator().getId(),
                saved.getLocation().getId(),
                saved.getLocation().getName(),
                saved.getFee()
        );

    }

    public MeetingResponseDto getMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약속을 찾을 수 없습니다."));

        return new MeetingResponseDto(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getMeetingDate(),
                meeting.getStartTime(),
                meeting.getEndTime(),
                meeting.getCreator().getId(),
                meeting.getLocation().getId(),
                meeting.getLocation().getName(),
                meeting.getFee()
        );
    }

    // 🟡 참석자 추가
    public void addParticipant(Long meetingId, MeetingParticipantRequestDto dto) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임을 찾을 수 없습니다."));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        MeetingParticipant participant = new MeetingParticipant();
        participant.setMeeting(meeting);
        participant.setUser(user);

        meetingParticipantRepository.save(participant);
    }

    // 🔴 참석자 삭제
    public void removeParticipant(Long meetingId, Long userId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        MeetingParticipant participant = meetingParticipantRepository
                .findByMeeting(meeting).stream()
                .filter(p -> p.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("참석자를 찾을 수 없습니다."));

        meetingParticipantRepository.delete(participant);
    }

    // 🟢 참석자 목록 조회
    public List<UserResponseDto> getParticipants(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임을 찾을 수 없습니다."));

        return meetingParticipantRepository.findByMeeting(meeting).stream()
                .map(p -> {
                    User u = p.getUser();
                    return new UserResponseDto(
                            u.getId(), u.getUsername(), u.getName(),
                            u.getBirth(), u.getPhone(), u.getAddress(), u.getProfileImage()
                    );
                }).toList();
    }
    // 초대된 약속 목록 조회
    public List<MeetingResponseDto> getInvitedMeetings(Long userId) {
        List<Meeting> meetings = meetingRepository.findByParticipants_User_Id(userId);

        return meetings.stream()
                .map(meeting -> new MeetingResponseDto(
                        meeting.getId(),
                        meeting.getTitle(),
                        meeting.getMeetingDate(),
                        meeting.getStartTime(),
                        meeting.getEndTime(),
                        meeting.getCreator().getId(),
                        meeting.getLocation() != null ? meeting.getLocation().getId() : null,
                        meeting.getLocation() != null ? meeting.getLocation().getName() : null,
                        meeting.getFee()
                ))
                .collect(Collectors.toList());
    }

    private MeetingResponseDto toResponseDto(Meeting meeting) {
        return new MeetingResponseDto(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getMeetingDate(),
                meeting.getStartTime(),
                meeting.getEndTime(),
                meeting.getFee(),
                meeting.getLocation() != null ? meeting.getLocation().getId() : null,
                meeting.getLocation() != null ? meeting.getLocation().getName() : null,
                meeting.getCreator().getId()
        );
    }

    // 약속 수정
    public void updateMeeting(Long meetingId, MeetingRequestDto dto) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약속이 존재하지 않습니다."));

        meeting.setTitle(dto.getTitle());
        meeting.setMeetingDate(dto.getMeetingDate());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());
        meeting.setFee(dto.getFee());

        if (dto.getLocationId() != null) {
            Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));
            meeting.setLocation(location);
        }

        meetingRepository.save(meeting);
    }

    // 약속 삭제
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약속이 존재하지 않습니다."));
        meetingRepository.delete(meeting);
    }

    public List<MeetingResponseDto> getMeetingsByParticipant(Long userId) {
        List<MeetingParticipant> participations = meetingParticipantRepository.findByUserId(userId);

        return participations.stream()
                .map(p -> {
                    Meeting m = p.getMeeting();
                    return new MeetingResponseDto(
                            m.getId(),
                            m.getTitle(),
                            m.getMeetingDate(),
                            m.getStartTime(),
                            m.getEndTime(),
                            m.getCreator().getId(),
                            m.getLocation().getId(),
                            m.getLocation().getName(),
                            m.getFee()
                    );
                })
                .collect(Collectors.toList());
    }


}
