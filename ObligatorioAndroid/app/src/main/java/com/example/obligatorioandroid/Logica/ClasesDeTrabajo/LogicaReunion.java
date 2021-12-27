package com.example.obligatorioandroid.Logica.ClasesDeTrabajo;

import android.content.Context;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaReunion;
import com.example.obligatorioandroid.Persistencia.FabricaPersistencia;

import java.util.List;

public class LogicaReunion implements ILogicaReunion {
    private static LogicaReunion instancia;


    public static LogicaReunion getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new LogicaReunion(contexto);
        }

        return instancia;
    }

    private LogicaReunion(Context contexto) {
        this.contexto = contexto.getApplicationContext();
    }


    private Context contexto;

    @Override
    public DTReunion buscarReunion(String descripcion, DTEvento evento) throws ExcepcionPersonalizada {

        return FabricaPersistencia.getPersistenciaReunion(contexto).buscarReunion(descripcion, evento);

    }

    @Override
    public List<DTReunion> listarReuniones(DTEvento evento) throws ExcepcionPersonalizada {
        return FabricaPersistencia.getPersistenciaReunion(contexto).listarReuniones(evento);
    }

    @Override
    public void crearReunion(DTReunion reunion) throws ExcepcionPersonalizada {
        FabricaPersistencia.getPersistenciaReunion(contexto).crearReunion(reunion);

    }
}
