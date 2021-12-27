package com.example.obligatorioandroid.Compartidos.DataTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DTEvento implements Serializable {
    protected String titulo;
    protected String tipo;
    protected Date fecha;
    protected int duracion;
    protected int cantidadDeAsistentes;
    protected DTCliente cliente;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadDeAsistentes() {
        return cantidadDeAsistentes;
    }

    public void setCantidadDeAsistentes(int cantidadDeAsistentes) {
        this.cantidadDeAsistentes = cantidadDeAsistentes;
    }

    public DTCliente getCliente() {
        return cliente;
    }

    public void setCliente(DTCliente cliente) {
        this.cliente = cliente;
    }

    public DTEvento() {
    }

    public DTEvento(Date fecha, int duracion, String titulo, String tipo, int cantidadDeAsistentes, DTCliente cliente) {
        this.fecha = fecha;
        this.duracion = duracion;
        this.titulo = titulo;
        this.tipo = tipo;
        this.cantidadDeAsistentes = cantidadDeAsistentes;
        this.cliente = cliente;
    }
}
