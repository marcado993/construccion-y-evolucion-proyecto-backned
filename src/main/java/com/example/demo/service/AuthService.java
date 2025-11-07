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
 * Servicio para autenticación y gestión de usuarios
 */
@Service
public class AuthService {
    
    private final UserRepository userRepository;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Registra un nuevo usuario
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
     * Autentica un usuario
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
     * Obtiene un usuario por ID (sin contraseña)
     */
    public Optional<AuthResponse.UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
            .map(this::mapToUserDTO);
    }
    
    /**
     * Mapea User a UserDTO (sin contraseña)
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
