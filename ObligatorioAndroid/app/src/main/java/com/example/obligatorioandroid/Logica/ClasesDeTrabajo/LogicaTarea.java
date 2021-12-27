package com.example.obligatorioandroid.Logica.ClasesDeTrabajo;

import android.content.Context;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaTarea;
import com.example.obligatorioandroid.Persistencia.FabricaPersistencia;

import java.util.List;

public class LogicaTarea implements ILogicaTarea {
    private static LogicaTarea instancia;


    public static LogicaTarea getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new LogicaTarea(contexto);
        }

        return instancia;
    }

    private LogicaTarea(Context contexto) {
        this.contexto = contexto.getApplicationContext();
    }


    private Context contexto;

    @Override
    public void crearTarea(DTTarea tarea) throws Exception {
        FabricaPersistencia.getPersistenciaTarea(contexto).crearTarea(tarea);

    }

    @Override
    public void modificarTarea(DTTarea tarea) throws Exception {
        FabricaPersistencia.getPersistenciaTarea(contexto).modificarTarea(tarea);

    }

    @Override
    public DTTarea buscarTarea(String descripcion, DTEvento evento) throws Exception {
        return FabricaPersistencia.getPersistenciaTarea(contexto).buscarTarea(descripcion, evento);

    }

    @Override
    public List<DTTarea> listarTareas(DTEvento evento) throws Exception {
        return FabricaPersistencia.getPersistenciaTarea(contexto).listarTareas(evento);
    }
}
