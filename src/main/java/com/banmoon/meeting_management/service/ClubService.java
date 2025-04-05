package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.dto.ClubRequestDto;
import com.banmoon.meeting_management.dto.ClubResponseDto;
import com.banmoon.meeting_management.entity.Club;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.ClubRepository;
import com.banmoon.meeting_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    public ClubResponseDto createClub(ClubRequestDto requestDto) {
        User owner = userRepository.findById(requestDto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Club club = new Club();
        club.setName(requestDto.getName());
        club.setImageUrl(requestDto.getImageUrl());
        club.setRegion(requestDto.getRegion());
        club.setFeeDate(requestDto.getFeeDate());
        club.setOwner(owner);

        Club saved = clubRepository.save(club);

        return new ClubResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getImageUrl(),
                saved.getRegion(),
                saved.getFeeDate(),
                owner.getName()
        );
    }
}
