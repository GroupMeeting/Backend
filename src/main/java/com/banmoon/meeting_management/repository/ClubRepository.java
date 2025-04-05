package com.banmoon.meeting_management.repository;

import com.banmoon.meeting_management.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}