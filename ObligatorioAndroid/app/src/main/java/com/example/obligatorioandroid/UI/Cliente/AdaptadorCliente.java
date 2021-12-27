package com.example.obligatorioandroid.UI.Cliente;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTComercial;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTParticular;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Evento.AdaptadorEventos;

import java.util.HashMap;
import java.util.List;

public class AdaptadorCliente extends BaseAdapter {
    private Context contexto;
    private List<DTCliente> clientes;

    public AdaptadorCliente(Context contexto, List<DTCliente> clientes) throws Exception {
        this.contexto = contexto;
        this.clientes = clientes;

    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public DTCliente getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ClienteViewHolder clienteViewHolder;

        if (item == null) {
            LayoutInflater inflador = LayoutInflater.from(contexto);
            item = inflador.inflate(R.layout.listitem_cliente, null);
            clienteViewHolder = new ClienteViewHolder(item);
            item.setTag(clienteViewHolder);

        } else {
            clienteViewHolder = (ClienteViewHolder) item.getTag();
        }
        clienteViewHolder.enlazarClientes(clientes.get(position));

        return item;

    }


    protected class ClienteViewHolder {

        protected TextView tvIdCliente;
        protected TextView tvDato1;
        protected TextView tvDato2;


        public ClienteViewHolder(View vista) {
            tvIdCliente = (TextView) vista.findViewById(R.id.tvIdCliente);
            tvDato1 = (TextView) vista.findViewById(R.id.tvDato1);
            tvDato2 = (TextView) vista.findViewById(R.id.tvDato2);
        }

        public void enlazarClientes(DTCliente cliente) {

            tvIdCliente.setText(String.valueOf("ID: " + cliente.getId()));
            if (cliente instanceof DTParticular) {
                tvDato1.setText(String.valueOf("Cedula " + ((DTParticular) cliente).getCedula()));
                tvDato2.setText(String.valueOf("Nombre " + ((DTParticular) cliente).getNombreCompleto()));
            } else {
                tvDato1.setText(String.valueOf("RUT " + ((DTComercial) cliente).getRut()));
                tvDato2.setText(String.valueOf("Razon Social " + ((DTComercial) cliente).getRazonSocial()));

            }

        }

    }
}
