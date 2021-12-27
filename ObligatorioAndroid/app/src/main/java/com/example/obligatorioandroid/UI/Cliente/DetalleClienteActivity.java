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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTComercial;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTParticular;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionUI;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.Evento.EditarEventoActivity;
import com.example.obligatorioandroid.UI.MainActivity;

import java.util.List;

public class DetalleClienteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected TextView tvIdCliente;
    protected TextView tvDireccion;
    protected TextView tvTelefono;
    protected TextView tvEmail;
    protected TextView tvDato1;
    protected TextView tvDato2;

    protected EditText etIdCliente;
    protected EditText etDireccion;
    protected EditText etTelefono;
    protected EditText etEmail;
    protected EditText etDato1;
    protected EditText etDato2;

    protected Spinner spTipoCliente;

    protected Button btnCliente;

    DTCliente clienteSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        spTipoCliente = (Spinner) findViewById(R.id.spTipoCliente);

        ArrayAdapter<CharSequence> adaptadorTipoCliente = ArrayAdapter.createFromResource(this, R.array.clientes, android.R.layout.simple_spinner_item);
        adaptadorTipoCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoCliente.setAdapter(adaptadorTipoCliente);
        spTipoCliente.setOnItemSelectedListener(this);

        tvIdCliente = findViewById(R.id.tvIdCliente);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvEmail = findViewById(R.id.tvEmail);
        tvDato1 = findViewById(R.id.tvDato1);
        tvDato2 = findViewById(R.id.tvDato2);

        etIdCliente = findViewById(R.id.etIdCliente);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        etEmail = findViewById(R.id.etEmail);
        etDato1 = findViewById(R.id.etDato1);
        etDato2 = findViewById(R.id.etDato2);

        btnCliente = findViewById(R.id.btnCliente);


        clienteSeleccionado = (DTCliente) getIntent().getSerializableExtra(Constantes.EXTRA_CLIENTE);

        if (clienteSeleccionado != null) {
            etIdCliente.setText(String.valueOf(clienteSeleccionado.getId()));
            etIdCliente.setEnabled(false);
            etDato1.setEnabled(false);
            etDireccion.setText(clienteSeleccionado.getDireccion());
            etTelefono.setText(clienteSeleccionado.getTelefono());
            etEmail.setText(clienteSeleccionado.getEmail());
            btnCliente.setText("Modificar");
            spTipoCliente.setEnabled(false);
            if (clienteSeleccionado instanceof DTParticular) {
                spTipoCliente.setSelection(0);
                tvDato1.setText("Cedula");
                tvDato2.setText("Nombre completo");
                etDato1.setText(((DTParticular) clienteSeleccionado).getCedula());
                etDato2.setText(((DTParticular) clienteSeleccionado).getNombreCompleto());
            } else {
                spTipoCliente.setSelection(1);
                tvDato1.setText("RUT");
                tvDato2.setText("Razón social");
                etDato1.setText(((DTComercial) clienteSeleccionado).getRut());
                etDato2.setText(((DTComercial) clienteSeleccionado).getRazonSocial());
            }
        } else {
            tvIdCliente.setVisibility(View.INVISIBLE);
            etIdCliente.setVisibility(View.INVISIBLE);
        }
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contextual_evento, menu);
        if (clienteSeleccionado == null)
            menu.removeItem(R.id.mniEliminar);
        menu.removeItem(R.id.mniModificar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mniEliminar:
                try {
                    List<DTEvento> eventos = FabricaLogica.getLogicaEvento(getApplicationContext()).listarEventos();
                    for (DTEvento evento : eventos) {
                        if (evento.getCliente().getId() == clienteSeleccionado.getId())
                            FabricaLogica.getLogicaEvento(getApplicationContext()).eliminarEvento(evento);
                    }
                    FabricaLogica.getLogicaCliente(getApplicationContext()).eliminarCliente(clienteSeleccionado);
                    Toast.makeText(this, "Cliente eliminado con éxito y sus eventos asociados", Toast.LENGTH_SHORT).show();
                    Intent regresar = new Intent(getApplicationContext(), ClienteActivity.class);
                    startActivity(regresar);
                    finish();

                } catch (ExcepcionPersonalizada ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(this, "No se pudo eliminar el cliente.", Toast.LENGTH_SHORT).show();
                }
                return true;
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


    public void editarCliente(View view) throws Exception {
        try {
            if (clienteSeleccionado != null) {
                clienteSeleccionado.setDireccion(etDireccion.getText().toString());
                clienteSeleccionado.setEmail(etEmail.getText().toString());
                clienteSeleccionado.setTelefono(etTelefono.getText().toString());
                if (clienteSeleccionado instanceof DTParticular) {
                    ((DTParticular) clienteSeleccionado).setNombreCompleto(etDato2.getText().toString());
                } else {
                    ((DTComercial) clienteSeleccionado).setRazonSocial(etDato2.getText().toString());
                }

                FabricaLogica.getLogicaCliente(getApplicationContext()).modificarCliente(clienteSeleccionado);
                Toast.makeText(this, "Cliente modificado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                DTCliente cliente = null;
                if (spTipoCliente.getSelectedItemPosition() == 0)
                    cliente = new DTParticular(0, etDireccion.getText().toString(), etTelefono.getText().toString(), etEmail.getText().toString(), etDato1.getText().toString(), etDato2.getText().toString());
                else {
                    cliente = new DTComercial(0, etDireccion.getText().toString(), etTelefono.getText().toString(), etEmail.getText().toString(), etDato1.getText().toString(), etDato2.getText().toString());
                }
                FabricaLogica.getLogicaCliente(getApplicationContext()).agregarCliente(cliente);
                Toast.makeText(this, "Cliente agregado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                tvDato1.setText("Cedula");
                tvDato2.setText("Nombre completo");

                break;
            case 1:
                tvDato1.setText("RUT");
                tvDato2.setText("Razón social");
                break;
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}