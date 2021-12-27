package com.example.obligatorioandroid.UI.Tareas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
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
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Cliente.ClienteActivity;
import com.example.obligatorioandroid.UI.Constantes;
import com.example.obligatorioandroid.UI.Evento.DetalleEventoActivity;
import com.example.obligatorioandroid.UI.Evento.EditarEventoActivity;
import com.example.obligatorioandroid.UI.MainActivity;
import com.example.obligatorioandroid.UI.Reunion.EditarReunionActivity;
import com.example.obligatorioandroid.UI.Reunion.ReunionActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditarTareaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected TextView tvDescripcion;
    protected EditText etDescripcion;
    protected TextView tvDeadline;
    protected EditText etDeadline;
    protected CheckBox cbCompletada;
    protected Button btnAgregar;

    protected SimpleDateFormat formateadorFechas;
    DTTarea tarea;
    DTEvento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        tvDescripcion = findViewById(R.id.tvDescripcionTarea);
        etDescripcion = findViewById(R.id.etDescripcionTarea);
        tvDeadline = findViewById(R.id.tvDeadline);
        etDeadline = findViewById(R.id.etDeadline);
        cbCompletada = findViewById(R.id.cbCompletada);
        btnAgregar = findViewById(R.id.btnAgregarTarea);

        tarea = (DTTarea) getIntent().getSerializableExtra(Constantes.EXTRA_TAREA);
        evento = (DTEvento) getIntent().getSerializableExtra(Constantes.EXTRA_EVENTO);
        formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");

        if (tarea != null) {
            etDescripcion.setText(tarea.getDescripcion());
            etDeadline.setText(formateadorFechas.format(tarea.getDeadline()));
            cbCompletada.setChecked(tarea.isCompletado());
            etDescripcion.setEnabled(false);
            etDeadline.setEnabled(false);
            cbCompletada.setClickable(false);
            if (tarea.isCompletado() == false)
                btnAgregar.setText("Marcar Tarea como completada");
            else {
                btnAgregar.setEnabled(false);
                btnAgregar.setVisibility(View.INVISIBLE);
            }
        }

        etDeadline.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return etFechaOnTouch(v, event);
            }
        });

        etDeadline.addTextChangedListener(new TextWatcher() {
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

    public boolean etFechaOnTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (etDeadline.getRight() - etDeadline.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Calendar calendario = Calendar.getInstance();

                try {
                    int dia = Integer.parseInt(etDeadline.getText().toString().substring(0, 2));
                    int mes = Integer.parseInt(etDeadline.getText().toString().substring(3, 5)) - 1;
                    int anio = Integer.parseInt(etDeadline.getText().toString().substring(6, 10));

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
        if (etDeadline.getError() != null) {
            etDeadline.setError(null);
            etDeadline.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_search, 0);
        }
    }

    public void editarTarea(View view) {
        try {
            DTEvento eventito = null;
            Intent intencionRegresar = new Intent(getApplicationContext(), TareaActivity.class);
            if (tarea != null && tarea.isCompletado() == false) {
                tarea.setCompletado(true);
                eventito = tarea.getEvento();
                FabricaLogica.getLogicaTarea(getApplicationContext()).modificarTarea(tarea);
                Toast.makeText(this, "La tarea fue completada con éxito", Toast.LENGTH_SHORT).show();
                intencionRegresar = new Intent(getApplicationContext(), TareaActivity.class);
                intencionRegresar.putExtra(Constantes.EXTRA_EVENTO, eventito);
                startActivity(intencionRegresar);
                finish();
            } else {
                DTTarea detalleTarea = new DTTarea(etDescripcion.getText().toString(), formateadorFechas.parse(etDeadline.getText().toString()), cbCompletada.isChecked(), evento);

                FabricaLogica.getLogicaTarea(getApplicationContext()).crearTarea(detalleTarea);
                Toast.makeText(this, "La tarea fue agregada con éxito", Toast.LENGTH_SHORT).show();
                intencionRegresar = new Intent(getApplicationContext(), TareaActivity.class);
                intencionRegresar.putExtra(Constantes.EXTRA_EVENTO, evento);
                startActivity(intencionRegresar);
                finish();
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etDeadline.setText(String.format("%1$02d", dayOfMonth) + "/" + String.format("%1$02d", (month + 1)) + "/" + year);

        if (etDeadline.getError() != null) {
            etDeadline.setError(null);
            etDeadline.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_search, 0);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static class SelectorFechaIngreso extends DialogFragment {

        public static EditarTareaActivity.SelectorFechaIngreso newInstance(Calendar calendario) {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable(Constantes.ARGUMENTO_CALENDARIO, calendario);

            EditarTareaActivity.SelectorFechaIngreso fragmento = new EditarTareaActivity.SelectorFechaIngreso();
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

