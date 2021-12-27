package com.example.obligatorioandroid.UI.Gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.MainActivity;
import com.example.obligatorioandroid.UI.Reunion.ReunionActivity;

public class EditarGastoActivity extends AppCompatActivity {

    protected TextView tvMotivo;
    protected EditText etMotivo;
    protected TextView tvMonto;
    protected EditText etMonto;
    protected TextView tvProveedor;
    protected EditText etProveedor;
    protected Button btnGuardar;

    DTEvento evento;
    DTGasto gasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_gasto);

        tvMotivo = findViewById(R.id.tvMotivoGasto);
        etMotivo = findViewById(R.id.etMotivoGasto);
        tvMonto = findViewById(R.id.tvMonto);
        etMonto = findViewById(R.id.etMonto);
        tvProveedor = findViewById(R.id.tvProveedor);
        etProveedor = findViewById(R.id.etProveedor);

        gasto = (DTGasto) getIntent().getSerializableExtra(Constantes.EXTRA_GASTO);
        evento = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);

        if (gasto != null) {
            etMotivo.setText(gasto.getMotivo());
            etMotivo.setEnabled(false);
            etProveedor.setText(gasto.getProveedor());
            etProveedor.setEnabled(false);
            etMonto.setText(String.valueOf(gasto.getMonto()));
            etMonto.setEnabled(false);
            btnGuardar = findViewById(R.id.btnEditarGasto);

            btnGuardar.setVisibility(View.INVISIBLE);
            btnGuardar.setEnabled(false);

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

    public void btnGuardarOnClick(View view) {
        try {
            DTGasto detalleGasto = new DTGasto(etMotivo.getText().toString(), etProveedor.getText().toString(), Double.parseDouble(etMonto.getText().toString()), evento);
            FabricaLogica.getLogicaGasto(getApplicationContext()).crearGasto(detalleGasto);
            Toast.makeText(this, "El gasto fue agregado con éxito", Toast.LENGTH_SHORT).show();

            Intent intencionRegresar = new Intent(getApplicationContext(), GastosActivity.class);
            intencionRegresar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intencionRegresar.putExtra(Constantes.EXTRA_EVENTO, evento);

            startActivity(intencionRegresar);

            finish();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
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