package com.example.obligatorioandroid.Persistencia.Interfaces;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;

import java.util.List;

public interface IPersistenciaReunion {

    DTReunion buscarReunion(String descripcion, DTEvento evento) throws ExcepcionPersonalizada;

    List<DTReunion> listarReuniones(DTEvento evento) throws ExcepcionPersonalizada;

    void crearReunion(DTReunion reunion) throws ExcepcionPersonalizada;



}
