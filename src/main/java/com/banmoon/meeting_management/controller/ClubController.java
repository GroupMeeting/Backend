// ClubController.java
package com.banmoon.meeting_management.controller;

import com.banmoon.meeting_management.dto.ClubRequestDto;
import com.banmoon.meeting_management.dto.ClubResponseDto;
import com.banmoon.meeting_management.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<ClubResponseDto> createClub(@RequestBody ClubRequestDto requestDto) {
        ClubResponseDto created = clubService.createClub(requestDto);
        return ResponseEntity.ok(created);
    }
}
