package com.banmoon.meeting_management.repository;

import com.banmoon.meeting_management.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
