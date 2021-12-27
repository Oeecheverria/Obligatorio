package com.example.obligatorioandroid.UI.Evento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EditarEventoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected TextView tvTituloEvento;
    protected TextView tvTipo;
    protected TextView tvFecha;
    protected TextView tvDuracion;
    protected TextView tvAsistentes;

    protected EditText etTitulo;
    protected EditText etFecha;
    protected EditText etDuracion;
    protected EditText etAsistentes;

    protected Spinner spIdCliente;
    protected Spinner spTipoEvento;


    protected SimpleDateFormat formateadorFechas;
    DTEvento evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        tvTituloEvento = findViewById(R.id.tvTituloEvento);
        tvTipo = findViewById(R.id.tvTipo);
        tvFecha = findViewById(R.id.tvFecha);
        tvDuracion = findViewById(R.id.tvDuracion);
        tvAsistentes = findViewById(R.id.tvAsistentes);
        spIdCliente = findViewById(R.id.spIdCliente);

        etTitulo = findViewById(R.id.etTitulo);
        spTipoEvento = findViewById(R.id.spTipoEvento);
        etFecha = findViewById(R.id.etFecha);
        etDuracion = findViewById(R.id.etDuracion);
        etAsistentes = findViewById(R.id.etAsistentes);

        List<DTCliente> clientes = null;
        ArrayAdapter<DTCliente> adaptadorClientes = null;

        ArrayAdapter<CharSequence> adaptadorTipoEvento = ArrayAdapter.createFromResource(this, R.array.eventos, android.R.layout.simple_spinner_item);
        adaptadorTipoEvento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoEvento.setAdapter(adaptadorTipoEvento);

        try {

            clientes = FabricaLogica.getLogicaCliente(getApplicationContext()).listarClientes();
            adaptadorClientes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientes);
            adaptadorClientes.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spIdCliente.setAdapter(adaptadorClientes);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

        evento = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);
        formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");

        if (evento != null) {

            etTitulo.setText(evento.getTitulo());
            etTitulo.setEnabled(false);
            spTipoEvento.setEnabled(false);
            if (evento.getTipo().equalsIgnoreCase("Familiar"))
                spTipoEvento.setSelection(0);
            else if (evento.getTipo().equalsIgnoreCase("Deportivo"))
                spTipoEvento.setSelection(1);
            else if (evento.getTipo().equalsIgnoreCase("Empresarial"))
                spTipoEvento.setSelection(2);
            else if (evento.getTipo().equalsIgnoreCase("Social"))
                spTipoEvento.setSelection(3);
            else
                spTipoEvento.setSelection(4);
            etFecha.setText(formateadorFechas.format(evento.getFecha()));
            etDuracion.setText(String.valueOf(evento.getDuracion()));
            etAsistentes.setText(String.valueOf(evento.getCantidadDeAsistentes()));
        }

        etFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return etFechaOnTouch(v, event);
            }
        });

        etFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etFechaIngresoAfterTextChanged(s);
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contextual_evento, menu);
        menu.removeItem(R.id.mniEliminar);
        menu.removeItem(R.id.mniModificar);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean etFechaOnTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (etFecha.getRight() - etFecha.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Calendar calendario = Calendar.getInstance();

                try {
                    int dia = Integer.parseInt(etFecha.getText().toString().substring(0, 2));
                    int mes = Integer.parseInt(etFecha.getText().toString().substring(3, 5)) - 1;
                    int anio = Integer.parseInt(etFecha.getText().toString().substring(6, 10));

                    calendario.set(Calendar.DAY_OF_MONTH, dia);
                    calendario.set(Calendar.MONTH, mes);
                    calendario.set(Calendar.YEAR, anio);
                } catch (Exception ex) {

                }

                SelectorFechaIngreso.newInstance(calendario).show(getSupportFragmentManager(), Constantes.TAG_SELECTOR_FECHA_INGRESO);

                return true;
            }
        }

        return false;

    }

    public void etFechaIngresoAfterTextChanged(Editable s) {
        if (etFecha.getError() != null) {
            etFecha.setError(null);
            etFecha.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_search, 0);
        }
    }

    public void btnGuardarOnClick(View view) {
        try {
            int idcliente = spIdCliente.getSelectedItemPosition() + 1;

            DTCliente cliente = FabricaLogica.getLogicaCliente(getApplicationContext()).buscarCliente(idcliente);
            DTEvento detalleEvento = new DTEvento(formateadorFechas.parse(etFecha.getText().toString()), Integer.parseInt(etDuracion.getText().toString()), etTitulo.getText().toString(), spTipoEvento.getSelectedItem().toString(), Integer.parseInt(etAsistentes.getText().toString()), cliente);

            if (evento != null) {
                FabricaLogica.getLogicaEvento(getApplicationContext()).modificarEvento(detalleEvento);
                Toast.makeText(this, "El evento fue modificado con éxito", Toast.LENGTH_SHORT).show();

            } else {
                FabricaLogica.getLogicaEvento(getApplicationContext()).nuevoEvento(detalleEvento);
                Toast.makeText(this, "El evento fue agregado con éxito", Toast.LENGTH_SHORT).show();
            }

            Intent intencionRegresar = new Intent(getApplicationContext(), DetalleEventoActivity.class);
            intencionRegresar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intencionRegresar.putExtra(Constantes.EXTRA_EVENTO, detalleEvento);
            startActivity(intencionRegresar);
            finish();

        } catch (Exception ex) {
            if (evento != null)
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etFecha.setText(String.format("%1$02d", dayOfMonth) + "/" + String.format("%1$02d", (month + 1)) + "/" + year);

        if (etFecha.getError() != null) {
            etFecha.setError(null);
            etFecha.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_search, 0);
        }
    }

    public static class SelectorFechaIngreso extends DialogFragment {

        public static SelectorFechaIngreso newInstance(Calendar calendario) {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable(Constantes.ARGUMENTO_CALENDARIO, calendario);

            SelectorFechaIngreso fragmento = new SelectorFechaIngreso();
            fragmento.setArguments(argumentos);

            return fragmento;
        }

        private Calendar calendario;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            calendario = (Calendar) getArguments().getSerializable(Constantes.ARGUMENTO_CALENDARIO);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            int anio = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), anio, mes, dia);
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