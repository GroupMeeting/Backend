package com.banmoon.meeting_management.repository;

import com.banmoon.meeting_management.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
