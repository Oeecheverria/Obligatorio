package com.example.obligatorioandroid.Compartidos.DataTypes;

import java.io.Serializable;

public class DTParticular extends DTCliente implements Serializable {

    protected String cedula;
    protected String nombreCompleto;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public DTParticular(int id, String direccion, String telefono, String email, String cedula, String nombreCompleto) {
        super(id, direccion, telefono, email);
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
    }

    public DTParticular() {

    }

    @Override
    public String toString() {
        return "ID: " +getId() + " - Cedula: " + getCedula();
    }
}
