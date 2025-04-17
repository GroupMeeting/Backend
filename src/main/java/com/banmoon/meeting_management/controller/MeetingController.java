package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.dto.MeetingRequestDto;
import com.banmoon.meeting_management.dto.MeetingParticipantRequestDto;
import com.banmoon.meeting_management.dto.MeetingResponseDto;
import com.banmoon.meeting_management.dto.UserResponseDto;
import com.banmoon.meeting_management.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<?> createMeeting(@RequestBody MeetingRequestDto dto) {
        return ResponseEntity.ok(meetingService.createMeeting(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeeting(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeeting(id));
    }

    // 🟡 약속 참석자 추가
    @PostMapping("/{meetingId}/participants")
    public ResponseEntity<?> addParticipant(@PathVariable Long meetingId,
                                            @RequestBody MeetingParticipantRequestDto requestDto) {
        meetingService.addParticipant(meetingId, requestDto);
        return ResponseEntity.ok().body("참석자가 추가되었습니다.");
    }

    // 🔴 약속 참석자 삭제
    @DeleteMapping("/{meetingId}/participants/{userId}")
    public ResponseEntity<?> removeParticipant(@PathVariable Long meetingId,
                                               @PathVariable Long userId) {
        meetingService.removeParticipant(meetingId, userId);
        return ResponseEntity.ok().body("참석자가 약속에서 삭제되었습니다.");
    }

    // 🟢 약속 참석자 조회
    @GetMapping("/{meetingId}/participants")
    public ResponseEntity<List<UserResponseDto>> getParticipants(@PathVariable Long meetingId) {
        List<UserResponseDto> participants = meetingService.getParticipants(meetingId);
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/invited/{userId}")
    public ResponseEntity<List<MeetingResponseDto>> getInvitedMeetings(@PathVariable Long userId) {
        return ResponseEntity.ok(meetingService.getInvitedMeetings(userId));
    }


    // 약속 수정
    @PatchMapping("/{meetingId}")
    public ResponseEntity<?> updateMeeting(@PathVariable Long meetingId,
                                           @RequestBody MeetingRequestDto dto) {
        meetingService.updateMeeting(meetingId, dto);
        return ResponseEntity.ok(Map.of("message", "약속이 수정되었습니다."));
    }

    // 약속 삭제
    @DeleteMapping("/{meetingId}")
    public ResponseEntity<?> deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok(Map.of("message", "약속이 삭제되었습니다."));
    }

    @GetMapping("/users/{userId}/meetings")
    public ResponseEntity<List<MeetingResponseDto>> getUserMeetings(@PathVariable Long userId) {
        return ResponseEntity.ok(meetingService.getMeetingsByParticipant(userId));
    }

}
