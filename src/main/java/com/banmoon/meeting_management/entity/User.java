package com.banmoon.meeting_management.entity;

import io.grpc.netty.shaded.io.netty.channel.unix.PeerCredentials;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uid; // Firebase UID

    @Column(nullable = false, unique = true)
    private String email;

    private String displayName;

    private LocalDateTime createdAt;
}
