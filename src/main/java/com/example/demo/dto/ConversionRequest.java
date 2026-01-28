package com.example.demo.dto;

/**
 * DTO for conversion requests.
 */
public class ConversionRequest {
    private String texto;
    private String tipo; // "texto-a-braille" o "braille-a-texto"
    private String dispositivo;
    private String navegador;
    private String ipOrigen;

    /**
     * Default constructor.
     */
    public ConversionRequest() {}

    /**
     * Constructor with basic fields.
     *
     * @param texto the text to convert
     * @param tipo the conversion type
     */
    public ConversionRequest(String texto, String tipo) {
        this.texto = texto;
        this.tipo = tipo;
    }

    /**
     * Gets the text to convert.
     *
     * @return the text
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Sets the text to convert.
     *
     * @param texto the text
     */
    public void setTexto(String texto) {
        this.texto = texto;
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
}