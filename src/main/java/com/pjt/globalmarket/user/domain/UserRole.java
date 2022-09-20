package com.pjt.globalmarket.user.domain;

public enum UserRole {
    ROLE_USER("ROLE_USER"), ROLE_MANAGER("ROLE_MANAGER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
