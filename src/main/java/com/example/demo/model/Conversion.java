package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad para guardar historial de conversiones
 */
@Entity
@Table(name = "conversiones")
public class Conversion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "texto_original", columnDefinition = "TEXT", nullable = false)
    private String textoOriginal;
    
    @Column(name = "resultado", columnDefinition = "TEXT", nullable = false)
    private String resultado;
    
    @Column(name = "tipo", nullable = false)
    private String tipo; // "texto-a-braille" o "braille-a-texto"
    
    @Column(name = "longitud_original")
    private Integer longitudOriginal;
    
    @Column(name = "longitud_resultado")
    private Integer longitudResultado;
    
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
    
    @Column(name = "ip_origen")
    private String ipOrigen;
    
    @Column(name = "dispositivo")
    private String dispositivo;
    
    @Column(name = "navegador")
    private String navegador;
    
    @Column(name = "tiempo_conversion_ms")
    private Integer tiempoConversionMs;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // Constructores
    public Conversion() {
        this.fecha = LocalDateTime.now();
    }
    
    public Conversion(String textoOriginal, String resultado, String tipo) {
        this.textoOriginal = textoOriginal;
        this.resultado = resultado;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
        this.longitudOriginal = textoOriginal != null ? textoOriginal.length() : 0;
        this.longitudResultado = resultado != null ? resultado.length() : 0;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTextoOriginal() {
        return textoOriginal;
    }
    
    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
        this.longitudOriginal = textoOriginal != null ? textoOriginal.length() : 0;
    }
    
    public String getResultado() {
        return resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
        this.longitudResultado = resultado != null ? resultado.length() : 0;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public Integer getLongitudOriginal() {
        return longitudOriginal;
    }
    
    public void setLongitudOriginal(Integer longitudOriginal) {
        this.longitudOriginal = longitudOriginal;
    }
    
    public Integer getLongitudResultado() {
        return longitudResultado;
    }
    
    public void setLongitudResultado(Integer longitudResultado) {
        this.longitudResultado = longitudResultado;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public String getIpOrigen() {
        return ipOrigen;
    }
    
    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }
    
    public String getDispositivo() {
        return dispositivo;
    }
    
    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
    
    public String getNavegador() {
        return navegador;
    }
    
    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }
    
    public Integer getTiempoConversionMs() {
        return tiempoConversionMs;
    }
    
    public void setTiempoConversionMs(Integer tiempoConversionMs) {
        this.tiempoConversionMs = tiempoConversionMs;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
