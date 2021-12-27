package com.example.obligatorioandroid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.Persistencia.BDHelper;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Evento.AdaptadorEventos;
import com.example.obligatorioandroid.UI.Evento.DetalleEventoActivity;
import com.example.obligatorioandroid.UI.Evento.EditarEventoActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected GridView gvEventos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gvEventos = (GridView) findViewById(R.id.gvEventos);

        gvEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gvEventosOnItemClick(parent, view, position, id);

            }
        });

        registerForContextMenu(gvEventos);

    }

    protected void gvEventosOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        DTEvento evento = (DTEvento) parent.getItemAtPosition(position);

        Intent intencionDetalle = new Intent(getApplicationContext(), DetalleEventoActivity.class);

        intencionDetalle.putExtra(Constantes.EXTRA_EVENTO, evento);

        startActivity(intencionDetalle);
    }


    public void agregarEvento(View view) {
        Intent intencionEditarEvento = new Intent(getApplicationContext(), EditarEventoActivity.class);
        startActivity(intencionEditarEvento);
    }

    public void mostrarEventos() {
        try {

            List<DTEvento> eventos = FabricaLogica.getLogicaEvento(this).listarEventos();

            AdaptadorEventos adaptadorEventos = new AdaptadorEventos(this, eventos);

            gvEventos.setAdapter(adaptadorEventos);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mostrarEventos();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contextual_evento, menu);
        menu.removeItem(R.id.mniEliminar);
        menu.removeItem(R.id.mniModificar);
        menu.removeItem(R.id.mniEventos);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mniEventos:
                Intent intencionEventos = new Intent(getApplicationContext(), MainActivity.class);
                intencionEventos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intencionEventos);

                finish();

                return true;
            case R.id.mniClientes:
                Intent intencionClientes = new Intent(getApplicationContext(), ClienteActivity.class);
                intencionClientes.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intencionClientes);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
