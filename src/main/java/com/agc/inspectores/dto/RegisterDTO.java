package com.agc.inspectores.dto;

import com.agc.inspectores.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String dni;

    @Email
    private String email;

    @NotBlank
    private String password;

    private Role role;

    public RegisterDTO() {
    }

    public RegisterDTO(String username, String dni, String email, String password) {
        this.username = username;
        this.dni = dni;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
