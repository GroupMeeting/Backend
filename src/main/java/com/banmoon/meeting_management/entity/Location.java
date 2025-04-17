package com.banmoon.meeting_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 장소명
    private Double latitude;    // 위도
    private Double longitude;   // 경도

    @Enumerated(EnumType.STRING)
    private LocationCategory category;  // 장소 분류 (CAFE, RESTAURANT 등)

    private Float rating;       // 평점
}
