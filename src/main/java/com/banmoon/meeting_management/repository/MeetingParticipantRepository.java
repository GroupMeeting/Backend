package com.banmoon.meeting_management.repository;

import com.banmoon.meeting_management.entity.MeetingParticipant;
import com.banmoon.meeting_management.entity.Meeting;
import com.banmoon.meeting_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {

    // 사용자가 초대된 모든 약속
    List<MeetingParticipant> findByUser(User user);

    // 사용자 ID로 초대된 약속
    List<MeetingParticipant> findByUserId(Long userId);

    // 특정 약속에 초대된 참가자 목록
    List<MeetingParticipant> findByMeeting(Meeting meeting);

    // 중복 방지용 (약속과 사용자 기준으로 하나만 존재해야 한다면)
    boolean existsByMeetingAndUser(Meeting meeting, User user);

    // 삭제 시 사용
    void deleteByMeetingAndUser(Meeting meeting, User user);
}
