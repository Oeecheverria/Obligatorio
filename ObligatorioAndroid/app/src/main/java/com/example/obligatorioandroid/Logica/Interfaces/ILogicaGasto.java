package com.example.obligatorioandroid.Logica.Interfaces;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;

import java.util.List;

public interface ILogicaGasto {

    DTGasto buscarGasto(String motivo, DTEvento evento)throws ExcepcionPersonalizada;

    List<DTGasto> listarGastos(DTEvento evento)throws ExcepcionPersonalizada;

    void crearGasto(DTGasto gasto)throws ExcepcionPersonalizada;
}
