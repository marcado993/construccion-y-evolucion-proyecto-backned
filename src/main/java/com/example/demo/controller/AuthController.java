package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para autenticación de usuarios
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * POST /api/auth/register - Registrar nuevo usuario
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Validar campos requeridos
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(false, "El nombre de usuario es requerido", null));
        }
        
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(false, "El email es requerido", null));
        }
        
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(false, "La contraseña debe tener al menos 6 caracteres", null));
        }
        
        AuthResponse response = authService.register(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * POST /api/auth/login - Iniciar sesión
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Validar campos requeridos
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(false, "El email es requerido", null));
        }
        
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(false, "La contraseña es requerida", null));
        }
        
        AuthResponse response = authService.login(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    /**
     * GET /api/auth/user/{id} - Obtener datos de usuario por ID
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return authService.getUserById(id)
            .map(user -> ResponseEntity.ok(new AuthResponse(true, "Usuario encontrado", user)))
            .orElse(ResponseEntity.notFound().build());
    }
}
