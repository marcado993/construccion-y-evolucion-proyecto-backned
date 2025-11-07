package com.example.demo.dto;

/**
 * DTO para respuesta de autenticación
 */
public class AuthResponse {
    private boolean success;
    private String message;
    private UserDTO user;

    public AuthResponse() {
    }

    public AuthResponse(boolean success, String message, UserDTO user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    /**
     * DTO interno para datos de usuario (sin contraseña)
     */
    public static class UserDTO {
        private Long id;
        private String username;
        private String email;
        private String nombreCompleto;
        private String rol;
        private boolean activo;

        public UserDTO() {
        }

        public UserDTO(Long id, String username, String email, String nombreCompleto, String rol, boolean activo) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.nombreCompleto = nombreCompleto;
            this.rol = rol;
            this.activo = activo;
        }

        // Getters y setters
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

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }

        public boolean isActivo() {
            return activo;
        }

        public void setActivo(boolean activo) {
            this.activo = activo;
        }
    }
}
