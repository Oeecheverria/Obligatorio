package com.example.obligatorioandroid.Compartidos.DataTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class DTTarea implements Serializable {

    protected String descripcion;
    protected Date deadline;
    protected boolean completado;
    protected DTEvento evento;

    public DTEvento getEvento() {
        return evento;
    }

    public void setEvento(DTEvento evento) {
        this.evento = evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public DTTarea() {
    }

    public DTTarea(String descripcion, Date deadline, boolean completado, DTEvento evento) {
        this.descripcion = descripcion;
        this.deadline = deadline;
        this.completado = completado;
        this.evento = evento;
    }
}
