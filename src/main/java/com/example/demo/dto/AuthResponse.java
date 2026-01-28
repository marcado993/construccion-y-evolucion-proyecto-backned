package com.example.demo.dto;

/**
 * DTO for authentication response.
 */
public class AuthResponse {
    private boolean success;
    private String message;
    private UserDTO user;

    /**
     * Default constructor.
     */
    public AuthResponse() {
    }

    /**
     * Constructor with all fields.
     *
     * @param success whether the operation was successful
     * @param message the response message
     * @param user the user data
     */
    public AuthResponse(boolean success, String message, UserDTO user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    /**
     * Checks if the operation was successful.
     *
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status.
     *
     * @param success the success status
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the response message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the user data.
     *
     * @return the user DTO
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * Sets the user data.
     *
     * @param user the user DTO
     */
    public void setUser(UserDTO user) {
        this.user = user;
    }

    /**
     * Inner DTO for user data (without password).
     */
    public static class UserDTO {
        private Long id;
        private String username;
        private String email;
        private String nombreCompleto;
        private String rol;
        private boolean activo;

        /**
         * Default constructor.
         */
        public UserDTO() {
        }

        /**
         * Constructor with all fields.
         *
         * @param id the user ID
         * @param username the username
         * @param email the email
         * @param nombreCompleto the full name
         * @param rol the role
         * @param activo the active status
         */
        public UserDTO(Long id, String username, String email, String nombreCompleto, String rol, boolean activo) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.nombreCompleto = nombreCompleto;
            this.rol = rol;
            this.activo = activo;
        }

        /**
         * Gets the user ID.
         *
         * @return the ID
         */
        public Long getId() {
            return id;
        }

        /**
         * Sets the user ID.
         *
         * @param id the ID
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * Gets the username.
         *
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the username.
         *
         * @param username the username
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Gets the email.
         *
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * Sets the email.
         *
         * @param email the email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * Gets the full name.
         *
         * @return the full name
         */
        public String getNombreCompleto() {
            return nombreCompleto;
        }

        /**
         * Sets the full name.
         *
         * @param nombreCompleto the full name
         */
        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }

        /**
         * Gets the role.
         *
         * @return the role
         */
        public String getRol() {
            return rol;
        }

        /**
         * Sets the role.
         *
         * @param rol the role
         */
        public void setRol(String rol) {
            this.rol = rol;
        }

        /**
         * Checks if the user is active.
         *
         * @return true if active, false otherwise
         */
        public boolean isActivo() {
            return activo;
        }

        /**
         * Sets the active status.
         *
         * @param activo the active status
         */
        public void setActivo(boolean activo) {
            this.activo = activo;
        }
    }
}
