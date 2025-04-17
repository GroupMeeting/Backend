package com.banmoon.meeting_management.repository;

import com.banmoon.meeting_management.entity.Meeting;
import com.banmoon.meeting_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    // 이미 되어있을 가능도 있지만, 혹시 없으면 꼭 추가!
    List<Meeting> findByParticipants_User_Id(Long userId);

}
