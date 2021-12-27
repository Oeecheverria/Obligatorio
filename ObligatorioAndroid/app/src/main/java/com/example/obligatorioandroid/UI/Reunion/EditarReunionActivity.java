package com.example.obligatorioandroid.UI.Reunion;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.Evento.EditarEventoActivity;
import com.example.obligatorioandroid.UI.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditarReunionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected TextView tvDescripcionReunion;
    protected EditText etDescripcionReunion;
    protected TextView tvObjetivo;
    protected EditText etObjetivo;
    protected TextView tvFecha;
    protected EditText etFecha;
    protected TextView tvLugar;
    protected EditText etLugar;
    protected CheckBox cbNotificacion;
    protected Button btnAgregar;

    protected SimpleDateFormat formateadorFechas;
    DTReunion reunion;
    DTEvento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_reunion);

        tvDescripcionReunion = findViewById(R.id.tvDescripcionReunion);
        etDescripcionReunion = findViewById(R.id.etDescripcionReunion);
        tvObjetivo = findViewById(R.id.tvObjetivo);
        etObjetivo = findViewById(R.id.etObjetivo);
        tvFecha = findViewById(R.id.tvFecha);
        etFecha = findViewById(R.id.etFecha);
        tvLugar = findViewById(R.id.tvLugar);
        etLugar = findViewById(R.id.etLugar);

        cbNotificacion = findViewById(R.id.cbNotificacion);
        btnAgregar = findViewById(R.id.btnEditarReunion);

        reunion = (DTReunion) getIntent().getSerializableExtra(Constantes.EXTRA_REUNION);
        evento = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);


        formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");

        if (reunion != null) {
            etDescripcionReunion.setText(reunion.getDescripcion());
            etDescripcionReunion.setEnabled(false);
            etObjetivo.setText(reunion.getObjetivo());
            etObjetivo.setEnabled(false);

            etLugar.setText(reunion.getLugar());
            etLugar.setEnabled(false);

            etFecha.setText(formateadorFechas.format(reunion.getFechaYHora()));
            etFecha.setEnabled(false);

            cbNotificacion.setChecked(reunion.isEnviarNotificacion());
            cbNotificacion.setEnabled(false);

            btnAgregar.setEnabled(false);
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

                EditarEventoActivity.SelectorFechaIngreso.newInstance(calendario).show(getSupportFragmentManager(), Constantes.TAG_SELECTOR_FECHA_INGRESO);

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
            DTReunion detalleReunion = new DTReunion(etDescripcionReunion.getText().toString(), etObjetivo.getText().toString(), formateadorFechas.parse(etFecha.getText().toString()), etLugar.getText().toString(), cbNotificacion.isChecked(), evento);

            if (reunion == null) {
                FabricaLogica.getLogicaReunion(getApplicationContext()).crearReunion(detalleReunion);
                Toast.makeText(this, "La reunion fue agregada con éxito", Toast.LENGTH_SHORT).show();
            }

            Intent intencionRegresar = new Intent(getApplicationContext(), ReunionActivity.class);
            intencionRegresar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intencionRegresar.putExtra(Constantes.EXTRA_EVENTO, evento);

            startActivity(intencionRegresar);

            finish();

        } catch (Exception ex) {
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static class SelectorFechaIngreso extends DialogFragment {

        public static EditarReunionActivity.SelectorFechaIngreso newInstance(Calendar calendario) {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable(Constantes.ARGUMENTO_CALENDARIO, calendario);

            EditarReunionActivity.SelectorFechaIngreso fragmento = new EditarReunionActivity.SelectorFechaIngreso();
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