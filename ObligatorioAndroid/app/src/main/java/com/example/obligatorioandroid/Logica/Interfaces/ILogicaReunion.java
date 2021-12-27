package com.example.obligatorioandroid.Logica.Interfaces;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;

import java.util.List;

public interface ILogicaReunion {

    DTReunion buscarReunion(String descripcion, DTEvento evento) throws ExcepcionPersonalizada;

    List<DTReunion> listarReuniones(DTEvento evento) throws ExcepcionPersonalizada;

    void crearReunion(DTReunion reunion) throws ExcepcionPersonalizada;



}
