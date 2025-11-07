package com.example.demo.dto;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de señalética
 */
public class SenaleticaResponse {
    
    private Long id;
    private String titulo;
    private String textoOriginal;
    private String textoBraille;
    private String tipoSenaletica;
    private String formatoSalida;
    private Boolean altoContraste;
    private String urlArchivo;
    private LocalDateTime fechaCreacion;
    private Integer descargas;
    private boolean exito;
    private String mensaje;
    
    // Constructores
    public SenaleticaResponse() {}
    
    public SenaleticaResponse(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
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
    
    public boolean isExito() {
        return exito;
    }
    
    public void setExito(boolean exito) {
        this.exito = exito;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
