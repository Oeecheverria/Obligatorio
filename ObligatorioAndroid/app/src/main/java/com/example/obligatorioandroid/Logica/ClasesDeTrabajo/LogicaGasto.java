package com.example.obligatorioandroid.Logica.ClasesDeTrabajo;

import android.content.Context;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaGasto;
import com.example.obligatorioandroid.Persistencia.FabricaPersistencia;

import java.util.List;

public class LogicaGasto implements ILogicaGasto {
    private static LogicaGasto instancia;


    public static LogicaGasto getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new LogicaGasto(contexto);
        }

        return instancia;
    }

    private LogicaGasto(Context contexto) {
        this.contexto = contexto.getApplicationContext();
    }


    private Context contexto;


    @Override
    public DTGasto buscarGasto(String motivo, DTEvento evento) throws ExcepcionPersonalizada {
        return FabricaPersistencia.getPersistenciaGasto(contexto).buscarGasto(motivo, evento);

    }

    @Override
    public List<DTGasto> listarGastos(DTEvento evento) throws ExcepcionPersonalizada {
        return FabricaPersistencia.getPersistenciaGasto(contexto).listarGastos(evento);
    }

    @Override
    public void crearGasto(DTGasto gasto) throws ExcepcionPersonalizada {
        ValidadorDT.validarGasto(gasto);
        FabricaPersistencia.getPersistenciaGasto(contexto).crearGasto(gasto);

    }
}
