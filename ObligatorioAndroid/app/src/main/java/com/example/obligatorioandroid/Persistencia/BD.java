package com.example.obligatorioandroid.Persistencia;

import android.provider.BaseColumns;

final class BD {

    public static final String NOMBRE_BASE_DATOS = "GestionEventos.sqlite3";
    public static final int VERSION_BASE_DATOS = 1;

    public static final String EVENTOS = "Eventos";
    public static final String REUNIONES = "Reuniones";
    public static final String GASTOS = "Gastos";
    public static final String TAREAS = "Tareas";
    public static final String CLIENTES = "Clientes";
    public static final String PARTICULARES = "Particulares";
    public static final String COMERCIALES = "Comerciales";

    private BD() {
    }

    public static abstract class Eventos {

        public static final String TITULO = "Titulo";
        public static final String FECHA = "Fecha";
        public static final String IDCLIENTE = "IdCliente";
        public static final String DURACION = "Duracion";
        public static final String TIPO = "Tipo";
        public static final String CANTIDADDEASISTENTES = "CantidadDeAsistentes";

        public static final String[] COLUMNAS = {TITULO, FECHA, IDCLIENTE, DURACION, TIPO, CANTIDADDEASISTENTES};

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ")
                .append(EVENTOS).append(" (")
                .append(TITULO).append(" TEXT NOT NULL PRIMARY KEY, ")
                .append(FECHA).append(" TEXT NOT NULL, ")
                .append(IDCLIENTE).append(" INTEGER NOT NULL, ")
                .append(DURACION).append(" INTEGER NOT NULL, ")
                .append(TIPO).append(" TEXT NOT NULL, ")
                .append(CANTIDADDEASISTENTES).append(" INTEGER NOT NULL, ")
                .append("FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTES (ID) );")
                .toString();

        public static final String SQL_ELIMINAR_TABLA = new StringBuilder("DROP TABLE IF EXISTS ").append(EVENTOS).append(";").toString();

    }

    public static abstract class Reuniones {

        public static final String DESCRIPCION = "Descripcion";
        public static final String TITULOEVENTO = "TituloEvento";
        public static final String OBJETIVO = "Objetivo";
        public static final String FECHAYHORA = "FechaYHora";
        public static final String LUGAR = "Lugar";
        public static final String ENVIARNOTIFICACION = "EnviarNotificacion";

        public static final String[] COLUMNAS = {DESCRIPCION, TITULOEVENTO, OBJETIVO, FECHAYHORA, LUGAR, ENVIARNOTIFICACION};

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ")
                .append(REUNIONES).append(" (")
                .append(DESCRIPCION).append(" TEXT NOT NULL PRIMARY KEY, ")
                .append(TITULOEVENTO).append(" TEXT NOT NULL, ")
                .append(OBJETIVO).append(" TEXT NOT NULL, ")
                .append(FECHAYHORA).append(" INTEGER NOT NULL, ")
                .append(LUGAR).append(" TEXT NOT NULL, ")
                .append(ENVIARNOTIFICACION).append(" INTEGER NOT NULL, ")
                .append("FOREIGN KEY (TITULOEVENTO) REFERENCES EVENTOS (TITULO) );")
                .toString();

        public static final String SQL_ELIMINAR_TABLA = new StringBuilder("DROP TABLE IF EXISTS ").append(REUNIONES).append(";").toString();

    }

    public static abstract class Tareas {

        public static final String DESCRIPCION = "Descripcion";
        public static final String TITULOEVENTO = "TituloEvento";
        public static final String DEADLINE = "Deadline";
        public static final String COMPLETADA = "Completada";

        public static final String[] COLUMNAS = {DESCRIPCION, TITULOEVENTO, DEADLINE, COMPLETADA};

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ")
                .append(TAREAS).append(" (")
                .append(DESCRIPCION).append(" TEXT NOT NULL PRIMARY KEY, ")
                .append(TITULOEVENTO).append(" TEXT NOT NULL, ")
                .append(DEADLINE).append(" TEXT NOT NULL, ")
                .append(COMPLETADA).append(" INTEGER NOT NULL, ")
                .append("FOREIGN KEY (TITULOEVENTO) REFERENCES EVENTOS (TITULO) );")

                .toString();

        public static final String SQL_ELIMINAR_TABLA = new StringBuilder("DROP TABLE IF EXISTS ").append(TAREAS).append(";").toString();

    }

    public static abstract class Gastos {

        public static final String MOTIVO = "Motivo";
        public static final String TITULOEVENTO = "TituloEvento";
        public static final String PROVEEDOR = "Proveedor";
        public static final String MONTO = "Monto";

        public static final String[] COLUMNAS = {MOTIVO, TITULOEVENTO, PROVEEDOR, MONTO};

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ")
                .append(GASTOS).append(" (")
                .append(MOTIVO).append(" TEXT NOT NULL PRIMARY KEY, ")
                .append(TITULOEVENTO).append(" INTEGER NOT NULL, ")
                .append(PROVEEDOR).append(" TEXT NOT NULL, ")
                .append(MONTO).append(" REAL NOT NULL, ")
                .append("FOREIGN KEY (TITULOEVENTO) REFERENCES EVENTOS (TITULO) );")
                .toString();

        public static final String SQL_ELIMINAR_TABLA = new StringBuilder("DROP TABLE IF EXISTS ").append(GASTOS).append(";").toString();

    }

    public static abstract class Clientes implements BaseColumns {


        public static final String DIRECCION = "Direccion";
        public static final String TELEFONO = "Telefono";
        public static final String EMAIL = "Email";

        public static final String[] COLUMNAS = {_ID, DIRECCION, TELEFONO, EMAIL};

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ")
                .append(CLIENTES).append(" (")
                .append(_ID).append(" INTEGER NOT NULL PRIMARY KEY, ")
                .append(DIRECCION).append(" TEXT NOT NULL, ")
                .append(TELEFONO).append(" TEXT NOT NULL, ")
                .append(EMAIL).append(" TEXT NOT NULL); ")
                .toString();

        public static final String SQL_ELIMINAR_TABLA = new StringBuilder("DROP TABLE IF EXISTS ").append(CLIENTES).append(";").toString();

    }

    public static abstract class Particulares {

        public static final String CEDULA = "Cedula";
        public static final String IDCLIENTE = "IdCliente";
        public static final String NOMBRECOMPLETO = "NombreCompleto";

        public static final String[] COLUMNAS = {CEDULA, IDCLIENTE, NOMBRECOMPLETO};

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ")
                .append(PARTICULARES).append(" (")
                .append(CEDULA).append(" TEXT NOT NULL, ")
                .append(IDCLIENTE).append(" INTEGER NOT NULL, ")
                .append(NOMBRECOMPLETO).append(" TEXT NOT NULL, ")
                .append("PRIMARY KEY (CEDULA, IDCLIENTE), ")
                .append("FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTES (ID) ); ")
                .toString();

        public static final String SQL_ELIMINAR_TABLA = new StringBuilder("DROP TABLE IF EXISTS ").append(PARTICULARES).append(";").toString();

    }

    public static abstract class Comerciales {

        public static final String RUT = "Rut";
        public static final String IDCLIENTE = "IdCliente";
        public static final String RAZONSOCIAL = "RazonSocial";

        public static final String[] COLUMNAS = {RUT, IDCLIENTE, RAZONSOCIAL};

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ")
                .append(COMERCIALES).append(" (")
                .append(RUT).append(" TEXT NOT NULL, ")
                .append(IDCLIENTE).append(" INTEGER NOT NULL, ")
                .append(RAZONSOCIAL).append(" TEXT NOT NULL, ")
                .append("PRIMARY KEY (RUT, IDCLIENTE), ")
                .append("FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTES (ID) ); ")
                .toString();

        public static final String SQL_ELIMINAR_TABLA = new StringBuilder("DROP TABLE IF EXISTS ").append(COMERCIALES).append(";").toString();

    }
}
