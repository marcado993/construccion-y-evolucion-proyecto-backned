package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity for storing conversion history.
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
    
    /**
     * Default constructor.
     */
    public Conversion() {
        this.fecha = LocalDateTime.now();
    }
    
    /**
     * Constructor with basic fields.
     *
     * @param textoOriginal the original text
     * @param resultado the conversion result
     * @param tipo the conversion type
     */
    public Conversion(String textoOriginal, String resultado, String tipo) {
        this.textoOriginal = textoOriginal;
        this.resultado = resultado;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
        this.longitudOriginal = textoOriginal != null ? textoOriginal.length() : 0;
        this.longitudResultado = resultado != null ? resultado.length() : 0;
    }
    
    /**
     * Gets the conversion ID.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the conversion ID.
     *
     * @param id the ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the original text.
     *
     * @return the original text
     */
    public String getTextoOriginal() {
        return textoOriginal;
    }
    
    /**
     * Sets the original text.
     *
     * @param textoOriginal the original text
     */
    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
        this.longitudOriginal = textoOriginal != null ? textoOriginal.length() : 0;
    }
    
    /**
     * Gets the conversion result.
     *
     * @return the result
     */
    public String getResultado() {
        return resultado;
    }
    
    /**
     * Sets the conversion result.
     *
     * @param resultado the result
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
        this.longitudResultado = resultado != null ? resultado.length() : 0;
    }
    
    /**
     * Gets the conversion type.
     *
     * @return the type
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Sets the conversion type.
     *
     * @param tipo the type
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Gets the original text length.
     *
     * @return the length
     */
    public Integer getLongitudOriginal() {
        return longitudOriginal;
    }
    
    /**
     * Sets the original text length.
     *
     * @param longitudOriginal the length
     */
    public void setLongitudOriginal(Integer longitudOriginal) {
        this.longitudOriginal = longitudOriginal;
    }
    
    /**
     * Gets the result text length.
     *
     * @return the length
     */
    public Integer getLongitudResultado() {
        return longitudResultado;
    }
    
    /**
     * Sets the result text length.
     *
     * @param longitudResultado the length
     */
    public void setLongitudResultado(Integer longitudResultado) {
        this.longitudResultado = longitudResultado;
    }
    
    /**
     * Gets the conversion date.
     *
     * @return the date
     */
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    /**
     * Sets the conversion date.
     *
     * @param fecha the date
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Gets the origin IP.
     *
     * @return the IP
     */
    public String getIpOrigen() {
        return ipOrigen;
    }
    
    /**
     * Sets the origin IP.
     *
     * @param ipOrigen the IP
     */
    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }
    
    /**
     * Gets the device.
     *
     * @return the device
     */
    public String getDispositivo() {
        return dispositivo;
    }
    
    /**
     * Sets the device.
     *
     * @param dispositivo the device
     */
    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
    
    /**
     * Gets the browser.
     *
     * @return the browser
     */
    public String getNavegador() {
        return navegador;
    }
    
    /**
     * Sets the browser.
     *
     * @param navegador the browser
     */
    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }
    
    /**
     * Gets the conversion time in milliseconds.
     *
     * @return the time
     */
    public Integer getTiempoConversionMs() {
        return tiempoConversionMs;
    }
    
    /**
     * Sets the conversion time in milliseconds.
     *
     * @param tiempoConversionMs the time
     */
    public void setTiempoConversionMs(Integer tiempoConversionMs) {
        this.tiempoConversionMs = tiempoConversionMs;
    }
    
    /**
     * Gets the associated user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Sets the associated user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
