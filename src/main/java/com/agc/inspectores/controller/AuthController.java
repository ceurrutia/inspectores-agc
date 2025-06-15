package com.agc.inspectores.controller;

import com.agc.inspectores.dto.LoginDTO;
import com.agc.inspectores.dto.PasswordResetDTO;
import com.agc.inspectores.dto.PasswordResetRequestDTO;
import com.agc.inspectores.dto.RegisterDTO;
import com.agc.inspectores.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/auth")
    public class AuthController {

        @Autowired
        private AuthService authService;

        // Solo SUPERADMIN puede crear nuevos usuarios (ADMIN o SUPERADMIN)
        @PreAuthorize("hasRole('SUPERADMIN')")
        @PostMapping("/register")
        public ResponseEntity<String> registerUserBySuperadmin(@Valid @RequestBody RegisterDTO registerDTO) {
            // Role incluido en el DTO para decidir qué tipo de usuario creo
            String result = authService.registerByRole(registerDTO);
            return ResponseEntity.ok(result);
        }

        // Login
        @PostMapping("/login")
        public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
            String jwt = authService.login(loginDTO);
            return ResponseEntity.ok(jwt);
        }

        // Solicitar recuperación de contraseña (envía token)
        @PostMapping("/forgot-password")
        public ResponseEntity<String> forgotPassword(@Valid @RequestBody PasswordResetRequestDTO requestDTO) {
            String result = authService.initiatePasswordReset(requestDTO.getEmail());
            return ResponseEntity.ok(result);
        }

        // Resetear contraseña con token
        @PostMapping("/reset-password")
        public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetDTO resetDTO) {
            String result = authService.resetPassword(resetDTO);
            return ResponseEntity.ok(result);
        }
        
    }
