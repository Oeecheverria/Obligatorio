package com.example.obligatorioandroid.UI.Tareas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Gastos.AdaptadorGastos;

import java.util.List;

public class AdaptadorTareas extends BaseAdapter {

    private Context contexto;
    private List<DTTarea> tareas;

    public AdaptadorTareas(Context contexto, List<DTTarea> tareas) {
        this.contexto = contexto;
        this.tareas = tareas;
    }

    @Override
    public int getCount() {
        return tareas.size();
    }

    @Override
    public DTTarea getItem(int position) {
        return tareas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        TareaViewHolder tareaViewHolder;

        if (vista == null) {
            LayoutInflater inflador = LayoutInflater.from(contexto);
            vista = inflador.inflate(R.layout.listitem_tarea, null);

            tareaViewHolder = new TareaViewHolder(vista);
            vista.setTag(tareaViewHolder);
        } else {
            tareaViewHolder = (TareaViewHolder) vista.getTag();
        }

        tareaViewHolder.enlazarTarea(tareas.get(position));

        return vista;
    }

    protected class TareaViewHolder {

        protected TextView tvDescripcion;
        protected CheckBox chkCompletada;

        public TareaViewHolder(View vista) {
            tvDescripcion = (TextView) vista.findViewById(R.id.tvDescripcionTarea);
            chkCompletada = (CheckBox) vista.findViewById(R.id.cbCompletada);
        }

        public void enlazarTarea(DTTarea tarea) {
            tvDescripcion.setText(tarea.getDescripcion());
            chkCompletada.setChecked(tarea.isCompletado());

        }
    }


}
