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

    private boolean isValidEmail(String email) {
        // Valida que no sea nulo, que tenga un @ y que termine en un dominio válido (por ejemplo .com, .org, .net, .com.ar, etc)
        return email != null && email.matches("^[^@\\s]+@[^@\\s]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isStrongPassword(String password) {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }


    public String registerSuperAdmin(RegisterDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            return "Debe ingresar un email.";
        }
        System.out.println("Email recibido en registerSuperAdmin: " + dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Ya existe un usuario con ese email.";
        }

        if (!isValidEmail(dto.getEmail())) {
            return "El email no tiene un formato válido. Debe contener '@' y un dominio válido.";
        }

        if (!isStrongPassword(dto.getPassword())) {
            return "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial.";
        }

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

    public String registerByRole(RegisterDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            return "Debe ingresar un email.";
        }
        System.out.println("Email recibido en registerByRole: " + dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Ya existe un usuario con ese email.";
        }

        if (!isValidEmail(dto.getEmail())) {
            return "El email no tiene un formato válido. Debe contener '@' y un dominio válido.";
        }

        if (!isStrongPassword(dto.getPassword())) {
            return "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial.";
        }

        if (dto.getRole() == null) {
            return "Debe especificar un rol.";
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setDni(dto.getDni());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setEnabled(true);

        userRepository.save(user);
        return "Usuario " + dto.getRole().name() + " registrado correctamente.";
    }

    public String initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email no encontrado"));

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
