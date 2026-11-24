package com.itq.pruebasintegracionuiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para renderizar tarjetas de prueba en el RecyclerView.
 */
public class ModuloTestAdapter extends RecyclerView.Adapter<ModuloTestAdapter.TestViewHolder> {

    private List<CalculadoraModuloDto> listaModulos = new ArrayList<>();

    public void setModulos(List<CalculadoraModuloDto> nuevosModulos) {
        this.listaModulos = nuevosModulos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_modulo_test, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        CalculadoraModuloDto dto = listaModulos.get(position);
        holder.tvNombre.setText(dto.getNombreModulo());
        holder.tvCobertura.setText("Cobertura: " + dto.getPuntajeCobertura() + "%");
        holder.tvEstado.setText(dto.getEstadoValidacion());
    }

    @Override
    public int getItemCount() {
        return listaModulos.size();
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        TextView tvCobertura;
        TextView tvEstado;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreModuloItem);
            tvCobertura = itemView.findViewById(R.id.tvCoberturaItem);
            tvEstado = itemView.findViewById(R.id.tvEstadoItem);
        }
    }
}