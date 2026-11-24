package com.itq.pruebasintegracionuiapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de ejecutar la lógica de validación de módulos y pruebas.
 * Esta clase es el objetivo principal de las pruebas unitarias aisladas.
 */
public class ValidacionRepository {

    private static ValidacionRepository instancia;

    private ValidacionRepository() {
    }

    public static synchronized ValidacionRepository getInstancia() {
        if (instancia == null) {
            instancia = new ValidacionRepository();
        }
        return instancia;
    }

    /**
     * Valida el formato de la entrada y genera un DTO procesado.
     *
     * @param entradaTexto Nombre del módulo ingresado por el usuario.
     * @return CalculadoraModuloDto Objeto procesado con puntaje de cobertura.
     * @throws IllegalArgumentException Si la entrada es nula o vacía.
     */
    public CalculadoraModuloDto procesarValidacion(String entradaTexto) {
        if (entradaTexto == null || entradaTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del módulo no puede estar vacío");
        }

        String textoLimpio = entradaTexto.trim().toUpperCase();
        int longitud = textoLimpio.length();
        int puntajeCalculado = Math.min(longitud * 10, 100);
        String estado = (puntajeCalculado >= 60) ? "VALIDADO (PASÓ PRUEBAS)" : "REVISIÓN REQUERIDA";

        return new CalculadoraModuloDto(
                "TEST-" + System.currentTimeMillis() % 1000,
                textoLimpio,
                puntajeCalculado,
                estado
        );
    }

    /**
     * Genera una lista inicial de módulos de prueba predeterminados.
     */
    public List<CalculadoraModuloDto> obtenerModulosIniciales() {
        List<CalculadoraModuloDto> lista = new ArrayList<>();
        lista.add(new CalculadoraModuloDto("T1", "MÓDULO DE AUTENTICACIÓN", 95, "VALIDADO (PASÓ PRUEBAS)"));
        lista.add(new CalculadoraModuloDto("T2", "PERSISTENCIA ROOM DB", 88, "VALIDADO (PASÓ PRUEBAS)"));
        lista.add(new CalculadoraModuloDto("T3", "CONECTIVIDAD BLUETOOTH P2P", 70, "VALIDADO (PASÓ PRUEBAS)"));
        return lista;
    }
}