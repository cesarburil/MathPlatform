package br.com.cesarburil.mathBackend.auth.model;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    PREMIUM("premium"),
    ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
