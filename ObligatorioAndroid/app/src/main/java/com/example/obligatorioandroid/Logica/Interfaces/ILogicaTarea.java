package com.example.obligatorioandroid.Logica.Interfaces;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;

import java.util.List;

public interface ILogicaTarea {

    void crearTarea(DTTarea tarea) throws Exception;

    DTTarea buscarTarea(String descripcion, DTEvento evento) throws Exception;

    List<DTTarea> listarTareas(DTEvento evento) throws Exception;

    void modificarTarea(DTTarea tarea) throws Exception;


}
