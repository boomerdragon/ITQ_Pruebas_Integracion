package com.itq.pruebasintegracionuiapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * GUÍA DE PRUEBAS UNITARIAS LOCALES (JVM) PARA REPOSITORIO Y VIEWMODEL.
 * Ubicación: app/src/test/java/ (Pruebas unitarias).
 * Requisito: Se ejecutan directamente en la Máquina Virtual de Java (JVM) de la computadora.
 * No requieren emulador ni dispositivo físico, por lo que su ejecución es casi instantánea.
 * Patrón utilizado: AAA (Arrange - Act - Assert / Preparar - Ejecutar - Verificar).
 */
public class ValidacionViewModelTest {

    /**
     * Regla InstantTaskExecutorRule:
     * Obliga a los componentes de arquitectura (como LiveData) a ejecutar
     * todas las tareas en el mismo hilo de manera síncrona.
     * Sin esta regla, las llamadas a LiveData.setValue() fallan en pruebas unitarias JVM.
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ValidacionRepository repository;

    /**
     * La función anotada con @Before se ejecuta automáticamente antes de cada prueba (@Test).
     * Se usa para inicializar objetos y preparar un entorno limpio.
     */
    @Before
    public void setUp() {
        repository = ValidacionRepository.getInstancia();
    }

    /**
     * Prueba unitaria que verifica la conversión y el cálculo correcto de un módulo válido.
     */
    @Test
    public void procesarValidacion_entradaValida_retornaDtoProcesado() {
        // -------------------------------------------------------------------------------
        // 1. ARRANGE (Preparación): Configurar los datos de entrada requeridos
        // -------------------------------------------------------------------------------
        String entrada = "Modulo Seguridad";

        // -------------------------------------------------------------------------------
        // 2. ACT (Ejecución): Invocar la función que se desea evaluar
        // -------------------------------------------------------------------------------
        CalculadoraModuloDto dto = repository.procesarValidacion(entrada);

        // -------------------------------------------------------------------------------
        // 3. ASSERT (Verificación): Comprobar que los resultados coincidan con lo esperado
        // -------------------------------------------------------------------------------
        assertNotNull(dto); // El objeto retornado no debe ser nulo
        assertEquals("MODULO SEGURIDAD", dto.getNombreModulo()); // Debe transformarse a mayúsculas
        assertTrue(dto.getPuntajeCobertura() > 0); // El puntaje debe ser mayor a 0
        assertEquals("VALIDADO (PASÓ PRUEBAS)", dto.getEstadoValidacion()); // Debe confirmar estado válido
    }

    /**
     * Prueba unitaria que verifica que ingresar espacios en blanco
     * genera una excepción de tipo IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void procesarValidacion_entradaVacia_lanzaExcepcion() {
        // Act: La ejecución con espacios en blanco debe lanzar la excepción esperada
        repository.procesarValidacion("   ");
    }

    /**
     * Prueba unitaria que evalúa la interacción del ViewModel con LiveData.
     */
    @Test
    public void ejecutarValidacion_entradaValida_actualizaLiveData() {
        // Arrange: Crear instancia de Application y del ViewModel
        Application application = new Application();
        ValidacionViewModel viewModel = new ValidacionViewModel(application);

        // Act: Ejecutar la acción de validación en el ViewModel
        viewModel.ejecutarValidacion("Modulo Firebase");

        // Assert: Verificar la actualización correcta de los LiveData observados por la UI
        assertNotNull(viewModel.getResultadoValidacion().getValue());
        assertTrue(viewModel.getResultadoValidacion().getValue().contains("MODULO FIREBASE"));
        assertNotNull(viewModel.getModulos().getValue());
        assertEquals("MODULO FIREBASE", viewModel.getModulos().getValue().get(0).getNombreModulo());
    }
}
