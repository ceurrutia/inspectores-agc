package com.agc.inspectores.dto;

import jakarta.validation.constraints.NotBlank;

public class PasswordResetDTO {
    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;


    // Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
