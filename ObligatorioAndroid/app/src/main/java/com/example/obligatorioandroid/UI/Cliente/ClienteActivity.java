package com.example.obligatorioandroid.UI.Cliente;

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

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.Evento.AdaptadorEventos;
import com.example.obligatorioandroid.UI.Evento.DetalleEventoActivity;
import com.example.obligatorioandroid.UI.Evento.EditarEventoActivity;
import com.example.obligatorioandroid.UI.MainActivity;

import java.util.List;

public class ClienteActivity extends AppCompatActivity implements ClientesFragment.OnClienteSeleccionadoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contextual_evento, menu);
        menu.removeItem(R.id.mniEliminar);
        menu.removeItem(R.id.mniModificar);
        menu.removeItem(R.id.mniClientes);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClienteSeleccionado(DTCliente cliente) {
        DetalleClienteFragment detalleClienteFragment = (DetalleClienteFragment) getSupportFragmentManager().findFragmentById(R.id.frgDetalleCliente);
        if (detalleClienteFragment != null) {
            detalleClienteFragment.mostrarCliente(cliente);
        } else {
            Intent intencionDetalle = new Intent(getApplicationContext(), DetalleClienteActivity.class);
            intencionDetalle.putExtra(Constantes.EXTRA_CLIENTE, cliente);
            startActivity(intencionDetalle);
        }
    }

    @Override
    public void onAgregarCliente() {
        DetalleClienteFragment detalleClienteFragment = (DetalleClienteFragment) getSupportFragmentManager().findFragmentById(R.id.frgDetalleCliente);
        if (detalleClienteFragment != null) {
            detalleClienteFragment.mostrarCliente(new DTCliente());
        } else {
            Intent intencionEditarCliente = new Intent(getApplicationContext(), DetalleClienteActivity.class);
            startActivity(intencionEditarCliente);
        }
    }
}
