package com.example.obligatorioandroid.UI.Reunion;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdaptadorReuniones extends BaseAdapter {

    private Context contexto;
    private List<DTReunion> reuniones;

    public AdaptadorReuniones(Context contexto, List<DTReunion> reuniones) {
        this.contexto = contexto;
        this.reuniones = reuniones;
    }

    @Override
    public int getCount() {
        return reuniones.size();
    }

    @Override
    public DTReunion getItem(int position) {
        return reuniones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        ReunionViewHolder reunionViewHolder;

        if (vista == null) {
            LayoutInflater inflador = LayoutInflater.from(contexto);
            vista = inflador.inflate(R.layout.listitem_reunion, null);

            reunionViewHolder = new ReunionViewHolder(vista);
            vista.setTag(reunionViewHolder);
        } else {
            reunionViewHolder = (ReunionViewHolder) vista.getTag();
        }

        reunionViewHolder.enlazarReunion(reuniones.get(position));

        return vista;
    }

    protected class ReunionViewHolder {

        protected TextView tvDescripcion;
        protected TextView tvObjetivo;
        protected TextView tvFechaReunion;

        protected SimpleDateFormat formateadorFechas;


        public ReunionViewHolder(View vista) {
            tvDescripcion = (TextView) vista.findViewById(R.id.tvDescripcionReunion);
            tvObjetivo = (TextView) vista.findViewById(R.id.tvObjetivo);
            tvFechaReunion = (TextView) vista.findViewById(R.id.tvFechaReunion);
        }

        public void enlazarReunion(DTReunion reunion) {
            formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");

            tvDescripcion.setText(tvDescripcion.getText() + reunion.getDescripcion());
            tvObjetivo.setText(tvObjetivo.getText() + reunion.getObjetivo());
            tvFechaReunion.setText(tvFechaReunion.getText() + formateadorFechas.format(reunion.getFechaYHora()));
        }
    }


}
