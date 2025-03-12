package com.banmoon.meeting_management.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class FirebaseAuthentication extends AbstractAuthenticationToken {

    private final String uid;

    public FirebaseAuthentication(String uid) {
        super(Collections.emptyList()); // 빈 권한 리스트 전달
        this.uid = uid;
        setAuthenticated(true); // 인증된 상태로 설정
    }

    @Override
    public String getCredentials() {
        return ""; // Firebase 인증에서는 비밀번호 X
    }

    @Override
    public String getPrincipal() {
        return uid; // 사용자 UID 반환
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 기본적으로 빈 권한 리스트 반환
    }

}
