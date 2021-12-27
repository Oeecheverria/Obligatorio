package com.example.obligatorioandroid.Compartidos.DataTypes;

import java.io.Serializable;

public class DTComercial extends DTCliente implements Serializable {

    protected String rut;

    protected String razonSocial;

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public DTComercial(int id, String direccion, String telefono, String email, String rut, String razonSocial) {
        super(id, direccion, telefono, email);
        this.rut = rut;
        this.razonSocial = razonSocial;
    }

    @Override
    public String toString() {
        return "ID: " +getId() + " - RUT: " + getRut();
    }
}
