package com.example.obligatorioandroid.Logica.Interfaces;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;

import java.util.List;

public interface ILogicaEvento {

    void nuevoEvento(DTEvento evento) throws ExcepcionPersonalizada;

    void modificarEvento(DTEvento evento) throws ExcepcionPersonalizada;

    void eliminarEvento(DTEvento evento) throws ExcepcionPersonalizada;

    DTEvento buscarEvento(String descripcion) throws ExcepcionPersonalizada;

    List<DTEvento> listarEventos() throws ExcepcionPersonalizada;
}
