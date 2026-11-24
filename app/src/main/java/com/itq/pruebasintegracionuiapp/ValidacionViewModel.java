package com.itq.pruebasintegracionuiapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

/**
 * ViewModel que gestiona el estado de la UI y sobrevive a rotaciones de pantalla.
 * Expone LiveData para ser observado reactivamente por la Activity.
 */
public class ValidacionViewModel extends AndroidViewModel {

    private final ValidacionRepository repository;

    private final MutableLiveData<List<CalculadoraModuloDto>> modulosLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> resultadoValidacionLiveData = new MutableLiveData<>("Listo para ejecutar pruebas.");
    private final MutableLiveData<Boolean> cargandoLiveData = new MutableLiveData<>(false);

    public ValidacionViewModel(@NonNull Application application) {
        super(application);
        this.repository = ValidacionRepository.getInstancia();
        cargarModulosIniciales();
    }

    public LiveData<List<CalculadoraModuloDto>> getModulos() {
        return modulosLiveData;
    }

    public LiveData<String> getResultadoValidacion() {
        return resultadoValidacionLiveData;
    }

    public LiveData<Boolean> getCargando() {
        return cargandoLiveData;
    }

    public void cargarModulosIniciales() {
        List<CalculadoraModuloDto> lista = repository.obtenerModulosIniciales();
        modulosLiveData.setValue(lista);
    }

    /**
     * Ejecuta la validación del módulo ingresado y actualiza los LiveData.
     */
    public void ejecutarValidacion(String entrada) {
        cargandoLiveData.setValue(true);
        try {
            CalculadoraModuloDto dto = repository.procesarValidacion(entrada);
            resultadoValidacionLiveData.setValue("Éxito: " + dto.getNombreModulo() + " -> " + dto.getEstadoValidacion());

            List<CalculadoraModuloDto> listaActual = modulosLiveData.getValue();
            if (listaActual != null) {
                listaActual.add(0, dto);
                modulosLiveData.setValue(listaActual);
            }
        } catch (IllegalArgumentException e) {
            resultadoValidacionLiveData.setValue("Error: " + e.getMessage());
        } finally {
            cargandoLiveData.setValue(false);
        }
    }
}