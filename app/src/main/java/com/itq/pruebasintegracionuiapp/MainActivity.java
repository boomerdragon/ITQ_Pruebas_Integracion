package com.itq.pruebasintegracionuiapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Activity Principal que conecta la interfaz gráfica con el ViewModel.
 * Esta clase es el objetivo principal de las pruebas de UI con Espresso.
 */
public class MainActivity extends AppCompatActivity {

    private ValidacionViewModel viewModel;

    private EditText etEntradaModulo;
    private Button btnValidarModulo;
    private TextView tvResultadoValidacion;
    private RecyclerView rvModulosTest;
    private ModuloTestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de componentes visuales
        etEntradaModulo = findViewById(R.id.etEntradaModulo);
        btnValidarModulo = findViewById(R.id.btnValidarModulo);
        tvResultadoValidacion = findViewById(R.id.tvResultadoValidacion);
        rvModulosTest = findViewById(R.id.rvModulosTest);

        // Configuración de RecyclerView
        rvModulosTest.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ModuloTestAdapter();
        rvModulosTest.setAdapter(adapter);

        // Inicialización del ViewModel
        viewModel = new ViewModelProvider(this).get(ValidacionViewModel.class);

        // Observación reactiva de LiveData
        viewModel.getModulos().observe(this, modulos -> {
            adapter.setModulos(modulos);
        });

        viewModel.getResultadoValidacion().observe(this, resultado -> {
            tvResultadoValidacion.setText(resultado);
        });

        // Configuración de evento click del botón
        btnValidarModulo.setOnClickListener(v -> {
            String entrada = etEntradaModulo.getText().toString();
            viewModel.ejecutarValidacion(entrada);
            etEntradaModulo.setText("");
        });
    }
}