package com.banmoon.meeting_management.service;

import com.banmoon.meeting_management.entity.ClubRole;
import com.banmoon.meeting_management.dto.ClubMemberRequestDto;
import com.banmoon.meeting_management.dto.ClubMemberResponseDto;
import com.banmoon.meeting_management.dto.ClubRequestDto;
import com.banmoon.meeting_management.dto.ClubResponseDto;
import com.banmoon.meeting_management.entity.Club;
import com.banmoon.meeting_management.entity.ClubMember;
import com.banmoon.meeting_management.entity.User;
import com.banmoon.meeting_management.repository.ClubMemberRepository;
import com.banmoon.meeting_management.repository.ClubRepository;
import com.banmoon.meeting_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;

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

        ClubMember clubMember = new ClubMember();
        clubMember.setClub(saved);
        clubMember.setUser(owner);
        clubMember.setRole(ClubRole.LEADER);
        clubMemberRepository.save(clubMember);

        return new ClubResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getImageUrl(),
                saved.getRegion(),
                saved.getFeeDate(),
                owner.getName()
        );
    }

    public List<ClubResponseDto> getAllClubs() {
        return clubRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ClubResponseDto> getMyClubs(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<ClubMember> memberships = clubMemberRepository.findByUser(user);

        return memberships.stream()
                .map(m -> {
                    Club club = m.getClub();
                    return new ClubResponseDto(
                            club.getId(),
                            club.getName(),
                            club.getImageUrl(),
                            club.getRegion(),
                            club.getFeeDate(),
                            club.getOwner().getName()
                    );
                })
                .collect(Collectors.toList());
    }



    private ClubResponseDto toResponseDto(Club club) {
        return new ClubResponseDto(
                club.getId(),
                club.getName(),
                club.getImageUrl(),
                club.getRegion(),
                club.getFeeDate(),
                club.getOwner().getName()
        );
    }

    public ClubResponseDto getClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 동아리를 찾을 수 없습니다."));
        return toResponseDto(club);
    }

    public void addMember(Long clubId, ClubMemberRequestDto dto) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 동아리가 없습니다."));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        ClubMember member = new ClubMember();
        member.setClub(club);
        member.setUser(user);
        member.setRole(dto.getRole());

        clubMemberRepository.save(member);
    }

    public List<ClubMemberResponseDto> getMembers(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("동아리 없음"));

        List<ClubMember> members = clubMemberRepository.findByClub(club);
        return members.stream()
                .map(m -> new ClubMemberResponseDto(
                        m.getUser().getId(),
                        m.getUser().getName(),
                        m.getUser().getProfileImage(),
                        m.getRole()
                ))
                .toList();
    }

}
