package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a user in the system.
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(name = "nombre_completo", length = 100)
    private String nombreCompleto;
    
    @Column(length = 20)
    private String rol = "usuario";
    
    @Column
    private Boolean activo = true;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "ultima_sesion")
    private LocalDateTime ultimaSesion;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor with basic fields.
     *
     * @param username the username
     * @param email the email
     * @param password the password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rol = "usuario";
        this.activo = true;
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
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Gets the active status.
     *
     * @return true if active, false otherwise
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * Sets the active status.
     *
     * @param activo the active status
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * Gets the registration date.
     *
     * @return the registration date
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Sets the registration date.
     *
     * @param fechaRegistro the registration date
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Gets the last session date.
     *
     * @return the last session date
     */
    public LocalDateTime getUltimaSesion() {
        return ultimaSesion;
    }

    /**
     * Sets the last session date.
     *
     * @param ultimaSesion the last session date
     */
    public void setUltimaSesion(LocalDateTime ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }
}