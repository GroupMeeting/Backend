package com.banmoon.meeting_management.repository;

import com.banmoon.meeting_management.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByOwner_Id(Long ownerId);

}