package com.example.obligatorioandroid.Logica.ClasesDeTrabajo;

import android.content.Context;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaEvento;
import com.example.obligatorioandroid.Persistencia.FabricaPersistencia;

import java.util.List;

public class LogicaEvento implements ILogicaEvento {
    private static LogicaEvento instancia;


    public static LogicaEvento getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new LogicaEvento(contexto);
        }

        return instancia;
    }

    private LogicaEvento(Context contexto) {
        this.contexto = contexto.getApplicationContext();
    }


    private Context contexto;


    @Override
    public void nuevoEvento(DTEvento evento) throws ExcepcionPersonalizada {
        ValidadorDT.validarEvento(evento);
        FabricaPersistencia.getPersistenciaEvento(contexto).nuevoEvento(evento);

    }

    @Override
    public void modificarEvento(DTEvento evento) throws ExcepcionPersonalizada {
        ValidadorDT.validarEvento(evento);
        FabricaPersistencia.getPersistenciaEvento(contexto).modificarEvento(evento);
    }

    @Override
    public void eliminarEvento(DTEvento evento) throws ExcepcionPersonalizada {
        FabricaPersistencia.getPersistenciaEvento(contexto).eliminarEvento(evento);

    }

    @Override
    public DTEvento buscarEvento(String descripcion) throws ExcepcionPersonalizada {
        return FabricaPersistencia.getPersistenciaEvento(contexto).buscarEvento(descripcion);

    }

    @Override
    public List<DTEvento> listarEventos() throws ExcepcionPersonalizada {
        return FabricaPersistencia.getPersistenciaEvento(contexto).listarEventos();

    }
}
