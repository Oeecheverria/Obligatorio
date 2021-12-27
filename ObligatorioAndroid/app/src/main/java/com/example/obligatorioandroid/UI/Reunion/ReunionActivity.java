package com.example.obligatorioandroid.UI.Reunion;

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
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReunionActivity extends AppCompatActivity {

    protected ListView lvReuniones;
    DTEvento evento;
    protected TextView tvMensaje;
    List<DTReunion> reunionList = new ArrayList<>();
    List<DTReunion> reunionesFiltradas = new ArrayList<>();
    boolean filtrado = true;
    Button btnFiltrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reunion);

            evento = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);
            tvMensaje = findViewById(R.id.tvMensaje);
            reunionList = FabricaLogica.getLogicaReunion(getApplicationContext()).listarReuniones(evento);
            lvReuniones = findViewById(R.id.lvReuniones);
            btnFiltrar = findViewById(R.id.btnFiltroReunion);
            lvReuniones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    lvReunionesOnItemClick(parent, view, position, id);
                }
            });
            registerForContextMenu(lvReuniones);
        } catch (Exception ex) {

        }
    }

    private void lvReunionesOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        DTReunion reunion = (DTReunion) parent.getItemAtPosition(position);

        Intent intencionEditarReunion = new Intent(getApplicationContext(), EditarReunionActivity.class);
        intencionEditarReunion.putExtra(Constantes.EXTRA_REUNION, reunion);

        startActivity(intencionEditarReunion);

    }

    public void nuevaReunion(View view) {
        Intent intencionNuevaReunion = new Intent(getApplicationContext(), EditarReunionActivity.class);
        intencionNuevaReunion.putExtra(Constantes.EXTRA_EVENTO, evento);

        startActivity(intencionNuevaReunion);
    }

    protected void mostrarReuniones(List<DTReunion> listaReuniones) {
        try {
            if (listaReuniones.size() == 0) {
                tvMensaje.setText("El evento no tiene reuniones creadas aún");
                btnFiltrar.setVisibility(View.INVISIBLE);
                btnFiltrar.setEnabled(false);

            } else {
                btnFiltrar.setEnabled(true);
                btnFiltrar.setVisibility(View.VISIBLE);

                AdaptadorReuniones adaptadorReuniones = new AdaptadorReuniones(this, listaReuniones);
                lvReuniones.setAdapter(adaptadorReuniones);
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mostrarReuniones(reunionList);
    }

    public void filtrarReuniones(View view) throws ExcepcionPersonalizada {
        Date currentDate = new Date(System.currentTimeMillis());
        btnFiltrar.setEnabled(true);
        btnFiltrar.setVisibility(View.VISIBLE);

        if (filtrado == true) {
            reunionesFiltradas.clear();
            filtrado = false;
            for (DTReunion reunion : reunionList) {
                if (reunion.getFechaYHora().after(currentDate))
                reunionesFiltradas.add(reunion);
            }
            if (reunionesFiltradas.size() == 0)
                tvMensaje.setText("No hay reuniones pendientes para este evento");
            btnFiltrar.setText("Mostrar Todas");
            mostrarReuniones(reunionesFiltradas);
        } else {
            filtrado = true;
            btnFiltrar.setText("Mostrar Pendientes");
            mostrarReuniones(reunionList);
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
