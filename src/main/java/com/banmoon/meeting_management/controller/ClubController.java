// ClubController.java
package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.dto.ClubMemberRequestDto;
import com.banmoon.meeting_management.dto.ClubMemberResponseDto;
import com.banmoon.meeting_management.dto.ClubRequestDto;
import com.banmoon.meeting_management.dto.ClubResponseDto;
import com.banmoon.meeting_management.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ClubController.java
@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    // 기존 생성 API
    @PostMapping
    public ResponseEntity<ClubResponseDto> createClub(@RequestBody ClubRequestDto requestDto) {
        return ResponseEntity.ok(clubService.createClub(requestDto));
    }


    @GetMapping("/all")
    public ResponseEntity<List<ClubResponseDto>> getAllClubs() {
        List<ClubResponseDto> clubs = clubService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<ClubResponseDto>> getMyClubs(@PathVariable Long userId) {
        List<ClubResponseDto> clubs = clubService.getMyClubs(userId);
        return ResponseEntity.ok(clubs);
    }


    @GetMapping("/{clubId}")
    public ResponseEntity<ClubResponseDto> getClub(@PathVariable Long clubId) {
        return ResponseEntity.ok(clubService.getClub(clubId));
    }

    @PostMapping("/{clubId}/members")
    public ResponseEntity<?> addMember(@PathVariable Long clubId, @RequestBody ClubMemberRequestDto dto) {
        clubService.addMember(clubId, dto);
        return ResponseEntity.ok().body("멤버가 추가되었습니다.");
    }

    @GetMapping("/{clubId}/members")
    public ResponseEntity<List<ClubMemberResponseDto>> getMembers(@PathVariable Long clubId) {
        return ResponseEntity.ok(clubService.getMembers(clubId));
    }


}
