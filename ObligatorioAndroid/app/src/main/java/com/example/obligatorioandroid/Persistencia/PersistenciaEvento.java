package com.example.obligatorioandroid.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTParticular;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersistencia;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaEvento;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class PersistenciaEvento implements IPersistenciaEvento {

    private static PersistenciaEvento instancia;


    public static PersistenciaEvento getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PersistenciaEvento(contexto);
        }

        return instancia;
    }

    private Context contexto;
    private DateFormat formateadorFechas;

    private PersistenciaEvento(Context contexto) {
        this.contexto = contexto.getApplicationContext();
        formateadorFechas = new SimpleDateFormat("yyyy-MM-dd");

    }


    @Override
    public void nuevoEvento(DTEvento evento) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();

            if (buscarEvento(evento.getTitulo()) != null) {
                throw new ExcepcionPersistencia("El evento ya existe.");
            }

            ContentValues valores = new ContentValues();
            valores.put(BD.Eventos.TITULO, evento.getTitulo());
            valores.put(BD.Eventos.FECHA, formateadorFechas.format(evento.getFecha()));
            valores.put(BD.Eventos.IDCLIENTE, evento.getCliente().getId());
            valores.put(BD.Eventos.DURACION, evento.getDuracion());
            valores.put(BD.Eventos.TIPO, evento.getTipo());
            valores.put(BD.Eventos.CANTIDADDEASISTENTES, evento.getCantidadDeAsistentes());

            baseDatos.insertOrThrow(BD.EVENTOS, null, valores);

        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar el evento", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public void modificarEvento(DTEvento evento) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            if (buscarEvento(evento.getTitulo()) == null) {
                throw new ExcepcionPersistencia("El evento no existe.");
            }
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(BD.Eventos.FECHA, formateadorFechas.format(evento.getFecha()));
            valores.put(BD.Eventos.DURACION, evento.getDuracion());
            valores.put(BD.Eventos.TIPO, evento.getTipo());
            valores.put(BD.Eventos.CANTIDADDEASISTENTES, evento.getCantidadDeAsistentes());

            int filas = baseDatos.update(BD.EVENTOS, valores,BD.Eventos.TITULO + " = ?", new String[] { String.valueOf(evento.getTitulo()) });



        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar el evento", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public void eliminarEvento(DTEvento evento) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();

            int filasAfectadas = baseDatos.delete(BD.EVENTOS, BD.Eventos.TITULO + " = ?", new String[] { String.valueOf(evento.getTitulo()) });

            if (filasAfectadas < 1) {
                throw new ExcepcionPersistencia("El empleado no existe.");
            }
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo eliminar el evento.", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public DTEvento buscarEvento(String descripcion) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.EVENTOS, BD.Eventos.COLUMNAS, BD.Eventos.TITULO + " = ?", new String[]{String.valueOf(descripcion)}, null, null, null);
            DTEvento evento = null;

            if (datos.moveToNext()) {
                evento = (instanciarEvento(datos));

            }

            return evento;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener el evento.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public List<DTEvento> listarEventos() throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.EVENTOS, BD.Eventos.COLUMNAS, null, null, null, null, BD.Eventos.TITULO);
            List<DTEvento> listaEventos = new ArrayList<>();

            while (datos.moveToNext()) {
                listaEventos.add(instanciarEvento(datos));

            }

            return listaEventos;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar los eventos.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }


    public DTEvento instanciarEvento(Cursor datos) throws Exception {
        int columnaFecha = datos.getColumnIndex(BD.Eventos.FECHA);
        int columnaDuracion = datos.getColumnIndex(BD.Eventos.DURACION);
        int columnaTitulo = datos.getColumnIndex(BD.Eventos.TITULO);
        int columnaTipo = datos.getColumnIndex(BD.Eventos.TIPO);
        int columnaAsistentes = datos.getColumnIndex(BD.Eventos.CANTIDADDEASISTENTES);
        int columnaIdCliente = datos.getColumnIndex(BD.Eventos.IDCLIENTE);

        DTCliente cliente = FabricaPersistencia.getPersistenciaCliente(contexto.getApplicationContext()).buscarCliente(datos.getInt(columnaIdCliente));
        Date fecha = formateadorFechas.parse(datos.getString(columnaFecha));

        DTEvento evento = new DTEvento(fecha, datos.getInt(columnaDuracion), datos.getString(columnaTitulo), datos.getString(columnaTipo), datos.getInt(columnaAsistentes), cliente);

        return evento;
    }

}
