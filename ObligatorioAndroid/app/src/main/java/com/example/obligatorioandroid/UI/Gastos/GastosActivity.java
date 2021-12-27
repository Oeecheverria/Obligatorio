package com.example.obligatorioandroid.UI.Gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class GastosActivity extends AppCompatActivity {

    protected ListView lvGastos;
    DTEvento evento;
    protected TextView tvMensaje;
    protected TextView tvMontoTotal;
    List<DTGasto> gastoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gastos);

            evento = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);
            tvMensaje = findViewById(R.id.tvMensaje);
            tvMontoTotal = findViewById(R.id.tvTotalGastos);
            gastoList = FabricaLogica.getLogicaGasto(getApplicationContext()).listarGastos(evento);
            lvGastos = findViewById(R.id.lvGastos);
            lvGastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    lvGastosOnItemClick(parent, view, position, id);
                }
            });
            registerForContextMenu(lvGastos);
        } catch (Exception ex) {

        }
    }

    private void lvGastosOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        DTGasto gasto = (DTGasto) parent.getItemAtPosition(position);

        Intent intencionEditarGasto = new Intent(getApplicationContext(), EditarGastoActivity.class);
        intencionEditarGasto.putExtra(Constantes.EXTRA_GASTO, gasto);

        startActivity(intencionEditarGasto);

    }


    public void nuevoGasto(View view) {

        Intent intencionNuevoGasto = new Intent(getApplicationContext(), EditarGastoActivity.class);
        intencionNuevoGasto.putExtra(Constantes.EXTRA_EVENTO, evento);

        startActivity(intencionNuevoGasto);
    }

    protected void mostrarGastos(List<DTGasto> listaGastos) {
        try {
            if (listaGastos.size() == 0) {
                tvMensaje.setText("El evento no tiene gastos creados aún");

            } else {
                double monto = 0;
                for(DTGasto gasto: listaGastos ) {
                    monto+= gasto.getMonto();
                }
                tvMontoTotal.setText("");
                tvMontoTotal.setText("Monto Total de los gastos: " + (monto));
                AdaptadorGastos adaptadorGastos = new AdaptadorGastos(this, listaGastos);
                lvGastos.setAdapter(adaptadorGastos);
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mostrarGastos(gastoList);
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