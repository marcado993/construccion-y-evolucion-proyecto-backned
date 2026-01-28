package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service for authentication and user management.
 */
@Service
public class AuthService {
    
    private final UserRepository userRepository;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Registers a new user.
     *
     * @param request the registration request
     * @return the authentication response
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Validaciones
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(false, "El email ya está registrado", null);
        }
        
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse(false, "El nombre de usuario ya está en uso", null);
        }
        
        // Crear nuevo usuario
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // NOTA: En producción, encriptar la contraseña con BCrypt
        user.setPassword(request.getPassword()); 
        user.setNombreCompleto(request.getNombreCompleto());
        user.setRol("usuario");
        user.setActivo(true);
        
        User savedUser = userRepository.save(user);
        
        // Crear respuesta sin contraseña
        AuthResponse.UserDTO userDTO = mapToUserDTO(savedUser);
        return new AuthResponse(true, "Usuario registrado exitosamente", userDTO);
    }
    
    /**
     * Authenticates a user.
     *
     * @param request the login request
     * @return the authentication response
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        
        if (userOpt.isEmpty()) {
            return new AuthResponse(false, "Email o contraseña incorrectos", null);
        }
        
        User user = userOpt.get();
        
        // Verificar contraseña
        // NOTA: En producción, usar BCrypt para comparar
        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Email o contraseña incorrectos", null);
        }
        
        // Verificar si está activo
        if (!user.getActivo()) {
            return new AuthResponse(false, "Usuario desactivado. Contacta al administrador", null);
        }
        
        // Actualizar última sesión
        user.setUltimaSesion(LocalDateTime.now());
        userRepository.save(user);
        
        // Crear respuesta sin contraseña
        AuthResponse.UserDTO userDTO = mapToUserDTO(user);
        return new AuthResponse(true, "Login exitoso", userDTO);
    }
    
    /**
     * Gets a user by ID (without password).
     *
     * @param id the user ID
     * @return an optional user DTO
     */
    public Optional<AuthResponse.UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
            .map(this::mapToUserDTO);
    }
    
    /**
     * Maps User to UserDTO (without password).
     *
     * @param user the user entity
     * @return the user DTO
     */
    private AuthResponse.UserDTO mapToUserDTO(User user) {
        return new AuthResponse.UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getNombreCompleto(),
            user.getRol(),
            user.getActivo()
        );
    }
}
