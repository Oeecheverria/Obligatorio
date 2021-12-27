package com.example.obligatorioandroid.UI.Cliente;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTComercial;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTParticular;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.example.obligatorioandroid.UI.Constantes;

import java.util.List;

public class DetalleClienteFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static DetalleClienteFragment newInstance() {
        return new DetalleClienteFragment();
    }

    protected LinearLayout llfrgDetalleCliente;

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
    protected Button btnEliminar;

    DTCliente cliente;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_cliente, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spTipoCliente = (Spinner) getView().findViewById(R.id.spTipoCliente);

        String[] clientes = {"Particular", "Comercial"};
        ArrayAdapter<String> adaptadorDias = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, clientes);
        adaptadorDias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoCliente.setAdapter(adaptadorDias);
        spTipoCliente.setOnItemSelectedListener(this);

        tvIdCliente = getView().findViewById(R.id.tvIdCliente);
        tvDireccion = getView().findViewById(R.id.tvDireccion);
        tvTelefono = getView().findViewById(R.id.tvTelefono);
        tvEmail = getView().findViewById(R.id.tvEmail);
        tvDato1 = getView().findViewById(R.id.tvDato1);
        tvDato2 = getView().findViewById(R.id.tvDato2);
        llfrgDetalleCliente = getView().findViewById(R.id.llfrgDetalleCliente);

        etIdCliente = getView().findViewById(R.id.etIdCliente);
        etTelefono = getView().findViewById(R.id.etTelefono);
        etDireccion = getView().findViewById(R.id.etDireccion);
        etEmail = getView().findViewById(R.id.etEmail);
        etDato1 = getView().findViewById(R.id.etDato1);
        etDato2 = getView().findViewById(R.id.etDato2);

        btnCliente = getView().findViewById(R.id.btnCliente);
        btnEliminar = getView().findViewById(R.id.btnEliminar);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (cliente != null) {
                        cliente.setDireccion(etDireccion.getText().toString());
                        cliente.setEmail(etEmail.getText().toString());
                        cliente.setTelefono(etTelefono.getText().toString());
                        if (cliente instanceof DTParticular) {
                            ((DTParticular) cliente).setNombreCompleto(etDato2.getText().toString());
                        } else {
                            ((DTComercial) cliente).setRazonSocial(etDato2.getText().toString());
                        }

                        FabricaLogica.getLogicaCliente(getActivity().getApplicationContext()).modificarCliente(cliente);
                        Toast.makeText(getActivity().getApplicationContext(), "Cliente modificado con éxito", Toast.LENGTH_SHORT).show();
                        Intent regresar = new Intent(getActivity().getApplicationContext(), ClienteActivity.class);
                        startActivity(regresar);
                    } else {
                        cliente = null;
                        if (spTipoCliente.getSelectedItemPosition() == 0)
                            cliente = new DTParticular(0, etDireccion.getText().toString(), etTelefono.getText().toString(), etEmail.getText().toString(), etDato1.getText().toString(), etDato2.getText().toString());
                        else {
                            cliente = new DTComercial(0, etDireccion.getText().toString(), etTelefono.getText().toString(), etEmail.getText().toString(), etDato1.getText().toString(), etDato2.getText().toString());
                        }
                        FabricaLogica.getLogicaCliente(getActivity().getApplicationContext()).agregarCliente(cliente);
                        Toast.makeText(getActivity().getApplicationContext(), "Cliente agregado con éxito", Toast.LENGTH_SHORT).show();
                        Intent regresar = new Intent(getActivity().getApplicationContext(), ClienteActivity.class);
                        startActivity(regresar);
                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<DTEvento> eventos = FabricaLogica.getLogicaEvento(getActivity().getApplicationContext()).listarEventos();
                    for (DTEvento evento : eventos) {
                        if (evento.getCliente().getId() == cliente.getId())
                            FabricaLogica.getLogicaEvento(getActivity().getApplicationContext()).eliminarEvento(evento);
                    }
                    FabricaLogica.getLogicaCliente(getActivity().getApplicationContext()).eliminarCliente(cliente);
                    Toast.makeText(getActivity().getApplicationContext(), "Cliente eliminado con éxito y sus eventos asociados", Toast.LENGTH_SHORT).show();
                    Intent regresar = new Intent(getActivity().getApplicationContext(), ClienteActivity.class);
                    startActivity(regresar);

                } catch (ExcepcionPersonalizada ex) {
                    Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity().getApplicationContext(), "No se pudo eliminar el cliente.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void mostrarCliente(DTCliente clienteSeleccionado) {
        if (clienteSeleccionado.getId() != 0) {
            etIdCliente.setVisibility(View.VISIBLE);
            tvIdCliente.setVisibility(View.VISIBLE);
            cliente = clienteSeleccionado;
            etIdCliente.setText(String.valueOf(clienteSeleccionado.getId()));
            etIdCliente.setEnabled(false);
            etDireccion.setText(clienteSeleccionado.getDireccion());
            etTelefono.setText(clienteSeleccionado.getTelefono());
            etEmail.setText(clienteSeleccionado.getEmail());
            btnCliente.setText("Modificar");
            llfrgDetalleCliente.setVisibility(View.VISIBLE);
            spTipoCliente.setEnabled(false);
            btnEliminar.setVisibility(View.VISIBLE);

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
            llfrgDetalleCliente.setVisibility(View.VISIBLE);
            etIdCliente.setVisibility(View.INVISIBLE);
            etIdCliente.setEnabled(false);
            tvIdCliente.setVisibility(View.INVISIBLE);
            tvIdCliente.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnEliminar.setVisibility(View.INVISIBLE);
            btnCliente.setText("Agregar");
            etIdCliente.setText("");
            etDato1.setText("");
            etDato2.setText("");
            etDireccion.setText("");
            etEmail.setText("");
            etTelefono.setText("");
            spTipoCliente.setEnabled(true);


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

