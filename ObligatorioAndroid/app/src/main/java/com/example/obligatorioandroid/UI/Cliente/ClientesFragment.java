package com.example.obligatorioandroid.UI.Cliente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Logica.FabricaLogica;
import com.example.obligatorioandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ClientesFragment extends Fragment {

    public static ClientesFragment newInstance() {
        return new ClientesFragment();
    }

    protected ListView lvClientes;

    protected OnClienteSeleccionadoListener onClienteSeleccionadoListener;

    protected FloatingActionButton floatingActionButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnClienteSeleccionadoListener) {
            onClienteSeleccionadoListener = (OnClienteSeleccionadoListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClienteSeleccionadoListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cliente, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvClientes = getView().findViewById(R.id.lvClientes);
        floatingActionButton = getView().findViewById(R.id.btnAgregarCliente);
        try {
            List<DTCliente> clientes = FabricaLogica.getLogicaCliente(getActivity().getApplicationContext()).listarClientes();
            AdaptadorCliente adaptadorCliente = new AdaptadorCliente(getActivity().getApplicationContext(), clientes);

            lvClientes.setAdapter(adaptadorCliente);

            lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    lvClientesOnItemClick(parent, view, position, id);
                }
            });
            registerForContextMenu(lvClientes);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAgregarCliente();
                }
            });
            registerForContextMenu(floatingActionButton);

        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void lvClientesOnItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (onClienteSeleccionadoListener != null) {
            onClienteSeleccionadoListener.onClienteSeleccionado((DTCliente) parent.getItemAtPosition(position));
        }
    }

    public void onAgregarCliente() {
        if (onClienteSeleccionadoListener != null) {
            onClienteSeleccionadoListener.onAgregarCliente();
        }
    }


    public interface OnClienteSeleccionadoListener {
        void onClienteSeleccionado(DTCliente cliente);
        void onAgregarCliente();
    }
}



