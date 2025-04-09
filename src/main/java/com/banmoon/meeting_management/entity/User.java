package com.banmoon.meeting_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호 (추후 암호화 필요)

    private String name;
    private String birth;
    private String phone;
    private String address;
    private String profileImage;


}
