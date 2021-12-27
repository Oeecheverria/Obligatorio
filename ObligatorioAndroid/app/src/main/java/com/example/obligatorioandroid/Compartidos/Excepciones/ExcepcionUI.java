package com.example.obligatorioandroid.Compartidos.Excepciones;

public class ExcepcionUI extends ExcepcionPersonalizada{

    public ExcepcionUI(String mensaje) {
        super(mensaje);
    }

    public ExcepcionUI(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }
}
