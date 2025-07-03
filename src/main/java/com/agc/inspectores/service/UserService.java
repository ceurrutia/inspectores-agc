package com.agc.inspectores.service;

import com.agc.inspectores.dto.UserDTO;
import com.agc.inspectores.entity.User;
import com.agc.inspectores.enums.Role;
import com.agc.inspectores.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Crear usuario
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setDni(userDTO.getDni());
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        } else {
            user.setRole(Role.ADMIN); // Rol por defecto
        }// ojo, rol ADMIN, no SUPERADMIN
        user.setEnabled(true);

        // Encripta contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);

        // Crea DTO para retorno sin exponer la contraseña
        UserDTO dto = new UserDTO();
        dto.setId(savedUser.getId());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());
        dto.setPassword(null);  // se va el pasword, decile adios
        dto.setDni(savedUser.getDni());
        dto.setRole(savedUser.getRole());
        dto.setEnabled(savedUser.isEnabled());

        return dto;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setDni(user.getDni());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        return dto;
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return convertToDTO(userOpt.get());
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setDni(userDTO.getDni());
            user.setRole(userDTO.getRole());
            // user.setEnabled(userDTO.isEnabled());
            user.setEnabled(true);

            // Si viene contraseña y no está vacía, la actualizamos
            if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
                if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                }
            }

            User updatedUser = userRepository.save(user);
            return convertToDTO(updatedUser);
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
    }
}
