package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad para guardar señalética generada
 */
@Entity
@Table(name = "senaleticas")
public class Senaletica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Column(name = "texto_original", columnDefinition = "TEXT", nullable = false)
    private String textoOriginal;
    
    @Column(name = "texto_braille", columnDefinition = "TEXT", nullable = false)
    private String textoBraille;
    
    @Column(name = "tipo_senaletica")
    private String tipoSenaletica;
    
    @Column(name = "formato_salida")
    private String formatoSalida = "PDF";
    
    @Column(name = "tamano_fuente")
    private Integer tamanoFuente = 24;
    
    @Column(name = "color_fondo")
    private String colorFondo = "#FFFFFF";
    
    @Column(name = "color_texto")
    private String colorTexto = "#000000";
    
    @Column(name = "alto_contraste")
    private Boolean altoContraste = true;
    
    @Column(name = "url_archivo")
    private String urlArchivo;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "descargas")
    private Integer descargas = 0;
    
    @Column(name = "ultima_descarga")
    private LocalDateTime ultimaDescarga;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // Constructores
    public Senaletica() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Senaletica(String titulo, String textoOriginal, String textoBraille) {
        this.titulo = titulo;
        this.textoOriginal = textoOriginal;
        this.textoBraille = textoBraille;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getTextoOriginal() {
        return textoOriginal;
    }
    
    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }
    
    public String getTextoBraille() {
        return textoBraille;
    }
    
    public void setTextoBraille(String textoBraille) {
        this.textoBraille = textoBraille;
    }
    
    public String getTipoSenaletica() {
        return tipoSenaletica;
    }
    
    public void setTipoSenaletica(String tipoSenaletica) {
        this.tipoSenaletica = tipoSenaletica;
    }
    
    public String getFormatoSalida() {
        return formatoSalida;
    }
    
    public void setFormatoSalida(String formatoSalida) {
        this.formatoSalida = formatoSalida;
    }
    
    public Integer getTamanoFuente() {
        return tamanoFuente;
    }
    
    public void setTamanoFuente(Integer tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }
    
    public String getColorFondo() {
        return colorFondo;
    }
    
    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
    }
    
    public String getColorTexto() {
        return colorTexto;
    }
    
    public void setColorTexto(String colorTexto) {
        this.colorTexto = colorTexto;
    }
    
    public Boolean getAltoContraste() {
        return altoContraste;
    }
    
    public void setAltoContraste(Boolean altoContraste) {
        this.altoContraste = altoContraste;
    }
    
    public String getUrlArchivo() {
        return urlArchivo;
    }
    
    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Integer getDescargas() {
        return descargas;
    }
    
    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }
    
    public LocalDateTime getUltimaDescarga() {
        return ultimaDescarga;
    }
    
    public void setUltimaDescarga(LocalDateTime ultimaDescarga) {
        this.ultimaDescarga = ultimaDescarga;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    // Método auxiliar para incrementar descargas
    public void incrementarDescargas() {
        this.descargas++;
        this.ultimaDescarga = LocalDateTime.now();
    }
}
