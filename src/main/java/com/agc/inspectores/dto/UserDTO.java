package com.agc.inspectores.dto;

import com.agc.inspectores.enums.Role;

    public class UserDTO {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String dni;
        private Role role;
        private boolean enabled;

        public UserDTO() {
        }

        public UserDTO(Long id, String username, String email, String password, String dni, Role role, boolean enabled) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
            this.dni = dni;
            this.role = role;
            this.enabled = enabled;
        }

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public String getDni() {
            return dni;
        }
        public void setDni(String dni) {
            this.dni = dni;
        }
        public Role getRole() {
            return role;
        }
        public void setRole(Role role) {
            this.role = role;
        }
        public boolean isEnabled() {
            return enabled;
        }
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

    }
