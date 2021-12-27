package com.example.obligatorioandroid.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersistencia;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaReunion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistenciaReunion implements IPersistenciaReunion {

    private static PersistenciaReunion instancia;


    public static PersistenciaReunion getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PersistenciaReunion(contexto);
        }

        return instancia;
    }

    private Context contexto;
    private DateFormat formateadorFechas;


    private PersistenciaReunion(Context contexto) {
        this.contexto = contexto.getApplicationContext();
        formateadorFechas = new SimpleDateFormat("yyyy-MM-dd");

    }

    @Override
    public DTReunion buscarReunion(String descripcion, DTEvento evento) throws ExcepcionPersonalizada {

        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.REUNIONES, BD.Reuniones.COLUMNAS, BD.Reuniones.DESCRIPCION + " = ? AND " + BD.Reuniones.TITULOEVENTO + " = ?", new String[]{String.valueOf(descripcion), String.valueOf(evento.getTitulo())}, null, null, null);
            DTReunion reunion = null;

            if (datos.moveToNext()) {
                reunion = (instanciarReunion(datos));

            }

            return reunion;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener la reunión.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public List<DTReunion> listarReuniones(DTEvento evento) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.REUNIONES, BD.Reuniones.COLUMNAS, BD.Reuniones.TITULOEVENTO + " = ?", new String[]{String.valueOf(evento.getTitulo())}, null, null, null);
            List<DTReunion> listaReuniones = new ArrayList<>();

            while (datos.moveToNext()) {
                listaReuniones.add(instanciarReunion(datos));

            }

            return listaReuniones;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar las reuniones.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }


    @Override
    public void crearReunion(DTReunion reunion) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();

            if (buscarReunion(reunion.getDescripcion(),reunion.getEvento()) != null) {
                throw new ExcepcionPersistencia("La reunión ya existe.");
            }

            ContentValues valores = new ContentValues();
            valores.put(BD.Reuniones.DESCRIPCION, reunion.getDescripcion());
            valores.put(BD.Reuniones.TITULOEVENTO, reunion.getEvento().getTitulo());
            valores.put(BD.Reuniones.OBJETIVO, reunion.getObjetivo());
            valores.put(BD.Reuniones.FECHAYHORA, formateadorFechas.format(reunion.getFechaYHora()));
            valores.put(BD.Reuniones.LUGAR, reunion.getLugar());

            int notificacion = 0;
            if(reunion.isEnviarNotificacion())
                notificacion = 1;
            valores.put(BD.Reuniones.ENVIARNOTIFICACION, notificacion);



            baseDatos.insertOrThrow(BD.REUNIONES, null, valores);

        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar la reunión", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    public DTReunion instanciarReunion(Cursor datos) throws Exception {
        int columnaDescripcion = datos.getColumnIndex(BD.Reuniones.DESCRIPCION);
        int columnaTitulo = datos.getColumnIndex(BD.Reuniones.TITULOEVENTO);
        int columnaObjetivo = datos.getColumnIndex(BD.Reuniones.OBJETIVO);
        int columnaFecha = datos.getColumnIndex(BD.Reuniones.FECHAYHORA);
        int columnaLugar = datos.getColumnIndex(BD.Reuniones.LUGAR);
        int columnaNotificacion = datos.getColumnIndex(BD.Reuniones.ENVIARNOTIFICACION);


        DTEvento evento = FabricaPersistencia.getPersistenciaEvento(contexto.getApplicationContext()).buscarEvento(datos.getString(columnaTitulo));
        Date fecha = formateadorFechas.parse(datos.getString(columnaFecha));

        boolean notificacion;

        if (datos.getInt(columnaNotificacion) == 1)
            notificacion = true;
        else
            notificacion = false;

        DTReunion reunion = new DTReunion(datos.getString(columnaDescripcion), datos.getString(columnaObjetivo), fecha, datos.getString(columnaLugar), notificacion, evento);

        return reunion;
    }
}
