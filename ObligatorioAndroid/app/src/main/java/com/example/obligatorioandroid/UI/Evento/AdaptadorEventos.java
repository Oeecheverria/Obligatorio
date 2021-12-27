package com.example.obligatorioandroid.UI.Evento;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdaptadorEventos extends BaseAdapter {
    private Context contexto;
    private List<DTEvento> eventos;


    public AdaptadorEventos(Context contexto, List<DTEvento> eventos) throws Exception {
        this.contexto = contexto;
        this.eventos = eventos;

    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        EventoViewholder eventoViewholder;

        if (item == null) {
            LayoutInflater inflador = LayoutInflater.from(contexto);
            item = inflador.inflate(R.layout.listitem_evento, null);
            eventoViewholder = new EventoViewholder(item);
            item.setTag(eventoViewholder);

        } else {
            eventoViewholder = (EventoViewholder) item.getTag();
        }
        eventoViewholder.enlazarEventos(eventos.get(position));

        return item;

    }

    protected class EventoViewholder {


        private ImageView imgvAvatar;
        protected TextView tvTituloEvento;
        protected TextView tvIdCliente;

        public EventoViewholder(View vista) {
            imgvAvatar = (ImageView) vista.findViewById(R.id.imgvAvatar);
            tvTituloEvento = (TextView) vista.findViewById(R.id.tvTituloEvento);
            tvIdCliente = (TextView) vista.findViewById(R.id.tvNombreCliente);
        }

        public void enlazarEventos(DTEvento evento) {


            String uri = "@drawable/" + evento.getTitulo().toLowerCase();
            int imageResource = contexto.getResources().getIdentifier(uri, null, contexto.getPackageName());
            if (imageResource == 0) {
                uri = "@drawable/defecto";
                imageResource = contexto.getResources().getIdentifier(uri, null, contexto.getPackageName());
            }
            Drawable res = contexto.getResources().getDrawable(imageResource);

            imgvAvatar.setImageDrawable(res);
            tvTituloEvento.setText(String.valueOf("Titulo: " + evento.getTitulo()));
            tvIdCliente.setText(String.valueOf("ID Cliente: " + evento.getCliente().getId()));
        }

    }
}
