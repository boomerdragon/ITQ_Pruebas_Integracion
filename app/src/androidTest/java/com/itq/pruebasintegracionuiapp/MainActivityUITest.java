package com.itq.pruebasintegracionuiapp;

// Importaciones estáticas de Espresso para facilitar la lectura del código.
// Permite escribir onView(), withId(), click(), etc. sin anteponer la clase.
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * GUÍA DE PRUEBAS INSTRUMENTADAS DE INTERFAZ GRÁFICA (UI) CON ESPRESSO.
 * Ubicación: app/src/androidTest/java/ (Pruebas instrumentadas).
 * Requisito: Requiere ejecutarse en un dispositivo real o emulador Android (AVD).
 * Estructura de interacción en Espresso:
 * 1. BÚSQUEDA (ViewMatchers)   -> onView(withId(R.id.mi_vista))
 * 2. ACCIÓN (ViewActions)      -> .perform(click(), replaceText("hola"))
 * 3. VERIFICACIÓN (Assertions) -> .check(matches(isDisplayed()))
 * Sincronización automática: Espresso espera automáticamente a que el hilo principal (UI Thread)
 * esté libre antes de realizar la siguiente acción.
 */
@RunWith(AndroidJUnit4.class) // Indica a JUnit que ejecute esta clase como prueba de Android instrumentada.
public class MainActivityUITest {

    /**
     * Regla de prueba que inicia automáticamente MainActivity antes de cada prueba
     * y la destruye al finalizar.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Prueba que evalúa el flujo completo desde que se ingresa texto en la UI,
     * se presiona el botón de validación, hasta que el resultado aparece reflejado en la pantalla.
     */
    @Test
    public void verificarFlujoCompletoDeValidacionEnUI() {

        // -------------------------------------------------------------------------------
        // PASO 1: VERIFICACIÓN INICIAL (Buscar elemento -> Verificar visibilidad)
        // -------------------------------------------------------------------------------
        // Buscamos el botón mediante su ID de recurso (R.id.btnValidarModulo)
        // y comprobamos que sea visible en la pantalla.
        onView(withId(R.id.btnValidarModulo))
                .check(matches(isDisplayed()));

        // -------------------------------------------------------------------------------
        // PASO 2: INTERACCIÓN DE ENTRADA (Buscar EditText -> Escribir texto -> Cerrar teclado)
        // -------------------------------------------------------------------------------
        // Usamos replaceText() para asignar el texto directamente en el campo de entrada.
        // A diferencia de typeText(), replaceText() evita alteraciones
        // provocadas por la autocorrección y sugerencias del teclado del sistema.
        // closeSoftKeyboard() asegura que el teclado virtual no bloquee elementos.
        onView(withId(R.id.etEntradaModulo))
                .perform(replaceText("Modulo Firebase"), closeSoftKeyboard());

        // -------------------------------------------------------------------------------
        // PASO 3: ACCIÓN DE CLIC (Buscar Botón -> Simular pulsación)
        // -------------------------------------------------------------------------------
        // Simula la pulsación del botón de validación por parte del usuario.
        // Esto desencadena el evento onClick que llama al ViewModel y procesa los datos.
        onView(withId(R.id.btnValidarModulo))
                .perform(click());

        // -------------------------------------------------------------------------------
        // PASO 4: VERIFICACIÓN DE RESULTADO (Buscar TextView -> Comprobar contenido)
        // -------------------------------------------------------------------------------
        // Verifica que el TextView tvResultadoValidacion contenga el texto procesado.
        // Se utiliza CoreMatchers.anyOf() para admitir variantes con o sin acento (MODULO / MÓDULO)
        // permitiendo que la prueba sea estable en cualquier configuración regional.
        onView(withId(R.id.tvResultadoValidacion))
                .check(matches(withText(CoreMatchers.anyOf(
                        CoreMatchers.containsString("MODULO FIREBASE"),
                        CoreMatchers.containsString("MÓDULO FIREBASE")
                ))));
    }
}
