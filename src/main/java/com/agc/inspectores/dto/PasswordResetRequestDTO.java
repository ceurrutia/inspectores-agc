package com.agc.inspectores.dto;

import jakarta.validation.constraints.Email;

public class PasswordResetRequestDTO {
    @Email
    private String email;

    // Getter & Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
