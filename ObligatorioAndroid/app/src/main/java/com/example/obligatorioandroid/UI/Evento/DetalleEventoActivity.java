package com.example.obligatorioandroid.UI.Evento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.Gastos.GastosActivity;
import com.example.obligatorioandroid.UI.MainActivity;
import com.example.obligatorioandroid.UI.Reunion.ReunionActivity;
import com.example.obligatorioandroid.UI.Tareas.TareaActivity;

import java.text.SimpleDateFormat;

public class DetalleEventoActivity extends AppCompatActivity {

    protected TextView tvTituloEvento;
    protected TextView tvTipo;
    protected TextView tvFecha;
    protected TextView tvDuracion;
    protected TextView tvAsistentes;
    protected TextView tvIdCliente;
    protected ImageView imgvAvatar;


    DTEvento eventoSeleccionado;

    protected SimpleDateFormat formateadorFechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);
        tvTituloEvento = findViewById(R.id.tvTituloEvento);
        tvTipo = findViewById(R.id.tvTipo);
        tvFecha = findViewById(R.id.tvFecha);
        tvDuracion = findViewById(R.id.tvDuracion);
        tvAsistentes = findViewById(R.id.tvAsistentes);
        tvIdCliente = findViewById(R.id.tvIdCliente);
        imgvAvatar = findViewById(R.id.imgAvatar);

        eventoSeleccionado = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);

        formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");new SimpleDateFormat("dd/MM/yyyy");

        String uri = "@drawable/" + eventoSeleccionado.getTitulo().toLowerCase();
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        if (imageResource == 0) {
            uri = "@drawable/defecto";
            imageResource = getResources().getIdentifier(uri, null, getPackageName());
        }
        Drawable res = getResources().getDrawable(imageResource);

        imgvAvatar.setImageDrawable(res);

        tvTituloEvento.setText("Titulo del Evento: " + eventoSeleccionado.getTitulo());
        tvTipo.setText("Tipo : " + eventoSeleccionado.getTipo());
        tvFecha.setText("Fecha : " + formateadorFechas.format(eventoSeleccionado.getFecha()));
        tvDuracion.setText("Duración: " + String.valueOf(eventoSeleccionado.getDuracion()));
        tvAsistentes.setText("Cantidad de Asistentes: " + String.valueOf(eventoSeleccionado.getCantidadDeAsistentes()));
        tvIdCliente.setText("ID del cliente: " + String.valueOf(eventoSeleccionado.getCliente().getId()));

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contextual_evento, menu);

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
            case R.id.mniModificar:
                Intent intencionEvento = new Intent(getApplicationContext(), EditarEventoActivity.class);
                intencionEvento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intencionEvento.putExtra(Constantes.EXTRA_EVENTO, eventoSeleccionado);
                startActivity(intencionEvento);
                finish();

                return true;
            case R.id.mniClientes:
                Intent intencion = new Intent(getApplicationContext(), ClienteActivity.class);
                intencion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intencion);
                finish();

                return true;
            case R.id.mniEliminar:
                try {
                    FabricaLogica.getLogicaEvento(getApplicationContext()).eliminarEvento(eventoSeleccionado);
                    Toast.makeText(this, "Evento eliminado con éxito.", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (ExcepcionPersonalizada ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(this, "No se pudo eliminar el evento.", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gestionGastos(View view) {
        Intent intencionGastos = new Intent(getApplicationContext(), GastosActivity.class);
        intencionGastos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intencionGastos.putExtra(Constantes.EXTRA_EVENTO, eventoSeleccionado);
        startActivity(intencionGastos);
        finish();
    }

    public void gestionReuniones(View view) {
        Intent intencionReunion = new Intent(getApplicationContext(), ReunionActivity.class);
        intencionReunion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intencionReunion.putExtra(Constantes.EXTRA_EVENTO, eventoSeleccionado);
        startActivity(intencionReunion);
        finish();

    }

    public void gestionTareas(View view) {
        Intent intencionTareas = new Intent(getApplicationContext(), TareaActivity.class);
        intencionTareas.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intencionTareas.putExtra(Constantes.EXTRA_EVENTO, eventoSeleccionado);
        startActivity(intencionTareas);
        finish();
    }
}