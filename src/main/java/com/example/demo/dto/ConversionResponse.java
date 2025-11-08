package com.example.demo.dto;

/**
 * DTO para respuestas de conversión
 */
public class ConversionResponse {
    private Long id;
    private String textoOriginal;
    private String resultado;
    private String tipo;
    private boolean exito;
    private String mensaje;
    private Integer longitudOriginal;
    private Integer longitudResultado;
    private Integer tiempoConversionMs;
    private String fecha;

    public ConversionResponse() {}

    public ConversionResponse(String textoOriginal, String resultado, String tipo, boolean exito) {
        this.textoOriginal = textoOriginal;
        this.resultado = resultado;
        this.tipo = tipo;
        this.exito = exito;
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

    public Integer getTiempoConversionMs() {
        return tiempoConversionMs;
    }

    public void setTiempoConversionMs(Integer tiempoConversionMs) {
        this.tiempoConversionMs = tiempoConversionMs;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
