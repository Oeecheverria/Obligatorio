package com.example.obligatorioandroid.Logica;

import android.content.Context;

import com.example.obligatorioandroid.Logica.ClasesDeTrabajo.LogicaCliente;
import com.example.obligatorioandroid.Logica.ClasesDeTrabajo.LogicaEvento;
import com.example.obligatorioandroid.Logica.ClasesDeTrabajo.LogicaGasto;
import com.example.obligatorioandroid.Logica.ClasesDeTrabajo.LogicaReunion;
import com.example.obligatorioandroid.Logica.ClasesDeTrabajo.LogicaTarea;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaCliente;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaEvento;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaGasto;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaReunion;
import com.example.obligatorioandroid.Logica.Interfaces.ILogicaTarea;

public class FabricaLogica {

    public  static ILogicaEvento getLogicaEvento (Context contexto){
        return LogicaEvento.getInstancia(contexto.getApplicationContext());
    }

    public  static ILogicaCliente getLogicaCliente (Context contexto){
        return LogicaCliente.getInstancia(contexto.getApplicationContext());
    }

    public  static ILogicaTarea getLogicaTarea (Context contexto){
        return LogicaTarea.getInstancia(contexto.getApplicationContext());
    }

    public  static ILogicaReunion getLogicaReunion (Context contexto){
        return LogicaReunion.getInstancia(contexto.getApplicationContext());
    }

    public  static ILogicaGasto getLogicaGasto (Context contexto){
        return LogicaGasto.getInstancia(contexto.getApplicationContext());
    }
}
