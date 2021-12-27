package com.example.obligatorioandroid.UI.Tareas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.Evento.EditarEventoActivity;
import com.example.obligatorioandroid.UI.Gastos.EditarGastoActivity;
import com.example.obligatorioandroid.UI.MainActivity;
import com.example.obligatorioandroid.UI.Reunion.AdaptadorReuniones;
import com.example.obligatorioandroid.UI.Reunion.EditarReunionActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TareaActivity extends AppCompatActivity {

    protected ListView lvTareas;
    DTEvento evento;
    protected TextView tvMensaje;
    List<DTTarea> tareasList = new ArrayList<>();
    List<DTTarea> tareasFiltradas = new ArrayList<>();
    boolean filtrado = true;
    Button btnFiltrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tarea);

            evento = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);
            tvMensaje = findViewById(R.id.tvMensaje);
            tareasList = FabricaLogica.getLogicaTarea(getApplicationContext()).listarTareas(evento);
            btnFiltrar = findViewById(R.id.btnFiltroTarea);
            lvTareas = findViewById(R.id.lvTareas);
            lvTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    lvTareasOnItemClick(parent, view, position, id);
                }
            });
            registerForContextMenu(lvTareas);
        } catch (Exception ex) {

        }
    }

    private void lvTareasOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        DTTarea tarea = (DTTarea) parent.getItemAtPosition(position);

        Intent intencionEditarTarea = new Intent(getApplicationContext(), EditarTareaActivity.class);
        intencionEditarTarea.putExtra(Constantes.EXTRA_TAREA, tarea);

        startActivity(intencionEditarTarea);

    }

    public void filtrarTareas(View view) {
        try {
            btnFiltrar.setEnabled(true);
            btnFiltrar.setVisibility(View.VISIBLE);
            if (filtrado == true) {
                tareasFiltradas.clear();
                filtrado = false;
                for (DTTarea tarea : tareasList) {
                    if (!tarea.isCompletado())
                        tareasFiltradas.add(tarea);
                }
                if (tareasFiltradas.size() == 0)
                    tvMensaje.setText("No hay tareas pendientes para este evento");
                btnFiltrar.setText("Mostrar Todas");
                AdaptadorTareas adaptadorTareas = new AdaptadorTareas(this, tareasFiltradas);
                lvTareas.setAdapter(adaptadorTareas);
            } else {
                filtrado = true;
                btnFiltrar.setText("Mostrar Pendientes");
                mostrarTareas(tareasList);
                tvMensaje.setText("");
            }

        } catch (Exception ex) {

        }

    }

    public void nuevaTarea(View view) {

        Intent intencionNuevaTarea = new Intent(getApplicationContext(), EditarTareaActivity.class);
        intencionNuevaTarea.putExtra(Constantes.EXTRA_EVENTO, evento);

        startActivity(intencionNuevaTarea);
    }

    protected void mostrarTareas(List<DTTarea> listaTareas) {
        try {
            if (listaTareas.size() == 0) {
                tvMensaje.setText("El evento no tiene tareas creadas aún");
                btnFiltrar.setVisibility(View.INVISIBLE);
                btnFiltrar.setEnabled(false);

            } else {
                btnFiltrar.setEnabled(true);
                btnFiltrar.setVisibility(View.VISIBLE);

                AdaptadorTareas adaptadorTareas = new AdaptadorTareas(this, listaTareas);
                lvTareas.setAdapter(adaptadorTareas);
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
            mostrarTareas(tareasList);
        } catch (Exception ex) {

        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contextual_evento, menu);
        menu.removeItem(R.id.mniEliminar);
        menu.removeItem(R.id.mniModificar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mniEventos:
                Intent intencionRegresar = new Intent(getApplicationContext(), MainActivity.class);
                intencionRegresar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intencionRegresar);

                finish();
                return true;
            case R.id.mniClientes:
                Intent intencionClientes = new Intent(getApplicationContext(), ClienteActivity.class);
                intencionClientes.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intencionClientes);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



