package com.example.obligatorioandroid.UI.Gastos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdaptadorGastos extends BaseAdapter {

    private Context contexto;
    private List<DTGasto> gastos;

    public AdaptadorGastos(Context contexto, List<DTGasto> gastos) {
        this.contexto = contexto;
        this.gastos = gastos;
    }

    @Override
    public int getCount() {
        return gastos.size();
    }

    @Override
    public Object getItem(int position) {
        return gastos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        GastosViewHolder gastosViewHolder;

        if (vista == null) {
            LayoutInflater inflador = LayoutInflater.from(contexto);
            vista = inflador.inflate(R.layout.listitem_gasto, null);

            gastosViewHolder = new GastosViewHolder(vista);
            vista.setTag(gastosViewHolder);
        } else {
            gastosViewHolder = (GastosViewHolder) vista.getTag();
        }

        gastosViewHolder.enlazarGasto(gastos.get(position));

        return vista;
    }

    protected class GastosViewHolder {

        protected TextView tvMotivo;
        protected TextView tvMonto;

        public GastosViewHolder(View vista) {
            tvMotivo = (TextView) vista.findViewById(R.id.tvMotivoGasto);
            tvMonto = (TextView) vista.findViewById(R.id.tvMonto);
        }

        public void enlazarGasto(DTGasto gasto) {
            tvMotivo.setText(gasto.getMotivo());
            tvMonto.setText(String.valueOf(gasto.getMonto()));

        }
    }
}
