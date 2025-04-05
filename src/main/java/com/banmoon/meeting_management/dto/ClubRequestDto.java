package com.banmoon.meeting_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubRequestDto {
    private String name;
    private String imageUrl;
    private String region;
    private String feeDate;
    private Long ownerId;
}