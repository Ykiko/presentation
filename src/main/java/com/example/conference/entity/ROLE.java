package com.example.conference.entity;

import org.springframework.security.core.GrantedAuthority;

public enum ROLE implements GrantedAuthority {
    ADMIN,
    PRESENTER,
    LISTENER;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PRESENTER = "ROLE_PRESENTER";
    public static final String ROLE_LISTENER = "ROLE_LISTENER";

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
