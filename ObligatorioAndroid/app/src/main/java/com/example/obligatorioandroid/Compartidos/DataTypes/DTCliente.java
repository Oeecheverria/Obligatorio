package com.example.obligatorioandroid.Compartidos.DataTypes;

import java.io.Serializable;

public class DTCliente implements Serializable {

    protected int id;
    protected String direccion;
    protected String telefono;
    protected String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DTCliente(int id, String direccion, String telefono, String email) {
        this.id = id;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public DTCliente() {
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
