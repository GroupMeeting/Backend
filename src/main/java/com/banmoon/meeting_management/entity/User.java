package com.banmoon.meeting_management.entity;

import com.banmoon.meeting_management.domain.SocialLoginType;
import io.grpc.netty.shaded.io.netty.channel.unix.PeerCredentials;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)  // Enum을 문자열로 저장
    private SocialLoginType socialLoginType;

    private String profileImage;
}

