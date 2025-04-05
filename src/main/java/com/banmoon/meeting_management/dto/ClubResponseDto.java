package com.banmoon.meeting_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubResponseDto {
    private Long id;
    private String name;
    private String imageUrl;
    private String region;
    private String feeDate;
    private String ownerName;
}