package com.interview.testtask.entity;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
    ROLE_COURIER,ROLE_OPERATOR, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}