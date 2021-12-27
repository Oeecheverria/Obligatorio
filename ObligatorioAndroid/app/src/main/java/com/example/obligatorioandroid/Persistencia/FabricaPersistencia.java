package com.example.obligatorioandroid.Persistencia;

import android.content.Context;

import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaCliente;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaEvento;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaGasto;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaReunion;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaTarea;

public class FabricaPersistencia {

    public static IPersistenciaCliente getPersistenciaCliente(Context contexto) {
        return PersistenciaCliente.getInstancia(contexto);
    }
    public static IPersistenciaEvento getPersistenciaEvento(Context contexto) {
        return PersistenciaEvento.getInstancia(contexto);
    }
    public static IPersistenciaGasto getPersistenciaGasto(Context contexto) {
        return PersistenciaGasto.getInstancia(contexto);
    }

    public static IPersistenciaReunion getPersistenciaReunion(Context contexto) {
        return PersistenciaReunion.getInstancia(contexto);
    }

    public static IPersistenciaTarea getPersistenciaTarea (Context contexto){
        return PersistenciaTarea.getInstancia(contexto);
    }
}
