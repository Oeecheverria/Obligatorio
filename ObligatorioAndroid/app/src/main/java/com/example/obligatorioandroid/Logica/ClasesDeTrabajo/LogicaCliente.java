package com.example.obligatorioandroid.Logica.ClasesDeTrabajo;

import android.content.Context;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaCliente;
import com.example.obligatorioandroid.Persistencia.FabricaPersistencia;

import java.util.List;

public class LogicaCliente implements ILogicaCliente {
    private static LogicaCliente instancia;


    public static LogicaCliente getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new LogicaCliente(contexto);
        }

        return instancia;
    }

    private LogicaCliente(Context contexto) {
        this.contexto = contexto.getApplicationContext();
    }


    private Context contexto;

    @Override
    public List<DTCliente> listarClientes() throws ExcepcionPersonalizada {
        return FabricaPersistencia.getPersistenciaCliente(contexto).listarClientes();
    }

    @Override
    public DTCliente buscarCliente(int id) throws ExcepcionPersonalizada {
        return FabricaPersistencia.getPersistenciaCliente(contexto).buscarCliente(id);
    }

    @Override
    public void agregarCliente(DTCliente cliente) throws ExcepcionPersonalizada {
        ValidadorDT.validarCliente(cliente);
        FabricaPersistencia.getPersistenciaCliente(contexto).agregarCliente(cliente);
    }

    @Override
    public void modificarCliente(DTCliente cliente) throws ExcepcionPersonalizada {
        ValidadorDT.validarCliente(cliente);
        FabricaPersistencia.getPersistenciaCliente(contexto).modificarCliente(cliente);
    }

    @Override
    public void eliminarCliente(DTCliente cliente) throws ExcepcionPersonalizada {
        FabricaPersistencia.getPersistenciaCliente(contexto).eliminarCliente(cliente);

    }
}
