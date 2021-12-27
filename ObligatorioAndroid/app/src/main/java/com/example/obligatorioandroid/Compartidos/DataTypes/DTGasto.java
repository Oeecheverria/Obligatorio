package com.example.obligatorioandroid.Compartidos.DataTypes;

import java.io.Serializable;

public class DTGasto implements Serializable {

    protected String motivo;
    protected String proveedor;
    protected double monto;
    protected DTEvento evento;

    public DTEvento getEvento() {
        return evento;
    }

    public void setEvento(DTEvento evento) {
        this.evento = evento;
    }

    public String getMotivo() {
        return motivo;
    }


    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public DTGasto() {
    }

    public DTGasto(String motivo, String proveedor, double monto, DTEvento evento) {
        this.motivo = motivo;
        this.proveedor = proveedor;
        this.monto = monto;
        this.evento = evento;
    }
}
