package com.example.demo.dto;

/**
 * DTO para solicitudes de conversión
 */
public class ConversionRequest {
    private String texto;
    private String tipo; // "texto-a-braille" o "braille-a-texto"
    private String dispositivo;
    private String navegador;
    private String ipOrigen;

    public ConversionRequest() {}

    public ConversionRequest(String texto, String tipo) {
        this.texto = texto;
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    
    public String getIpOrigen() {
        return ipOrigen;
    }
    
    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }
}
