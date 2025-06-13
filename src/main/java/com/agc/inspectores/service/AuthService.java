package com.agc.inspectores.service;

import com.agc.inspectores.dto.LoginDTO;
import com.agc.inspectores.dto.PasswordResetDTO;
import com.agc.inspectores.dto.RegisterDTO;
import com.agc.inspectores.enums.Role;
import com.agc.inspectores.entity.User;
import com.agc.inspectores.repository.UserRepository;
import com.agc.inspectores.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public String login(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Genera JWT
        return jwtUtils.generateToken(user);
    }


    public String registerSuperAdmin(RegisterDTO dto) {
        // Validar si el email existe
        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Ya existe un usuario con ese email.";
        }

        // Crea nuevo usuario
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setDni(dto.getDni());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.SUPERADMIN);
        user.setEnabled(true);

        userRepository.save(user);
        return "Usuario SUPERADMIN registrado correctamente.";
    }

    public String initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email no encontrado"));

        // Genera token
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);
        return "Token de recuperación generado: " + token;
    }

    public String resetPassword(PasswordResetDTO dto) {
        User user = userRepository.findByResetToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetToken(null);

        userRepository.save(user);

        return "Contraseña actualizada correctamente.";
    }

}
