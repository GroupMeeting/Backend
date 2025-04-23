package com.banmoon.meeting_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String date;

    private String startTime;

    private String endTime;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Club club;

    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
