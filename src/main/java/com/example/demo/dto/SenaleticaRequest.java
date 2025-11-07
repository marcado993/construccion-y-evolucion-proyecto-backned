package com.example.demo.dto;

/**
 * DTO para solicitud de creación de señalética
 */
public class SenaleticaRequest {
    
    private String titulo;
    private String textoOriginal;
    private String tipoSenaletica;
    private String formatoSalida;
    private Boolean altoContraste;
    private Integer tamanoFuente;
    private String colorFondo;
    private String colorTexto;
    
    // Constructores
    public SenaleticaRequest() {}
    
    public SenaleticaRequest(String titulo, String textoOriginal) {
        this.titulo = titulo;
        this.textoOriginal = textoOriginal;
    }
    
    // Getters y Setters
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
}
