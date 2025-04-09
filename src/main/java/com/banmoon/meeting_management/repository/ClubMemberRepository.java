package com.banmoon.meeting_management.repository;

import com.banmoon.meeting_management.entity.Club;
import com.banmoon.meeting_management.entity.ClubMember;
import com.banmoon.meeting_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findByClub(Club club);
    List<ClubMember> findByUser(User user);

}
