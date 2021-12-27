package com.example.obligatorioandroid.Persistencia.Interfaces;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;

import java.util.List;

public interface IPersistenciaCliente {

    List<DTCliente> listarClientes() throws ExcepcionPersonalizada;

    DTCliente buscarCliente(int id) throws ExcepcionPersonalizada;

    void agregarCliente(DTCliente cliente) throws ExcepcionPersonalizada;

    void modificarCliente(DTCliente cliente) throws ExcepcionPersonalizada;

    void eliminarCliente(DTCliente cliente) throws ExcepcionPersonalizada;
}
