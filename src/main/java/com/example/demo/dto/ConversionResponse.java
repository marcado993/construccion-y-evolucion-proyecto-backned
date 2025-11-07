package com.example.demo.dto;

/**
 * DTO para respuestas de conversión
 */
public class ConversionResponse {
    private String textoOriginal;
    private String resultado;
    private String tipo;
    private boolean exito;
    private String mensaje;

    public ConversionResponse() {}

    public ConversionResponse(String textoOriginal, String resultado, String tipo, boolean exito) {
        this.textoOriginal = textoOriginal;
        this.resultado = resultado;
        this.tipo = tipo;
        this.exito = exito;
    }

    // Getters y Setters
    public String getTextoOriginal() {
        return textoOriginal;
    }

    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
