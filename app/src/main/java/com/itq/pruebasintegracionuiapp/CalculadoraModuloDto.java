package com.itq.pruebasintegracionuiapp;

import androidx.annotation.Keep;

/**
 * Modelo DTO para encapsular los datos de prueba de módulos en la aplicación.
 * La anotación @Keep evita que ProGuard/R8 ofusque los campos durante la compilación.
 */
@Keep
public class CalculadoraModuloDto {

    private String id;
    private String nombreModulo;
    private int puntajeCobertura;
    private String estadoValidacion;

    /**
     * Constructor vacío obligatorio para serialización/deserialización JSON.
     */
    public CalculadoraModuloDto() {
    }

    /**
     * Constructor parametrizado para la creación de instancias DTO.
     */
    public CalculadoraModuloDto(String id, String nombreModulo, int puntajeCobertura, String estadoValidacion) {
        this.id = id;
        this.nombreModulo = nombreModulo;
        this.puntajeCobertura = puntajeCobertura;
        this.estadoValidacion = estadoValidacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public int getPuntajeCobertura() {
        return puntajeCobertura;
    }

    public void setPuntajeCobertura(int puntajeCobertura) {
        this.puntajeCobertura = puntajeCobertura;
    }

    public String getEstadoValidacion() {
        return estadoValidacion;
    }

    public void setEstadoValidacion(String estadoValidacion) {
        this.estadoValidacion = estadoValidacion;
    }
}