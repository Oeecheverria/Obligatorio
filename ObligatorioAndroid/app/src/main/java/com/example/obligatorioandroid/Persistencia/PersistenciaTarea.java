package com.example.obligatorioandroid.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersistencia;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaTarea;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistenciaTarea implements IPersistenciaTarea {

    private static PersistenciaTarea instancia;


    public static PersistenciaTarea getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PersistenciaTarea(contexto);
        }

        return instancia;
    }

    private Context contexto;
    private DateFormat formateadorFechas;


    private PersistenciaTarea(Context contexto) {
        this.contexto = contexto.getApplicationContext();
        formateadorFechas = new SimpleDateFormat("yyyy-MM-dd");

    }

    @Override
    public void crearTarea(DTTarea tarea) throws Exception {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();

            if (buscarTarea(tarea.getDescripcion(), tarea.getEvento()) != null) {
                throw new ExcepcionPersistencia("La tarea ya existe.");
            }

            ContentValues valores = new ContentValues();
            valores.put(BD.Tareas.DESCRIPCION, tarea.getDescripcion());
            valores.put(BD.Tareas.TITULOEVENTO, tarea.getEvento().getTitulo());
            valores.put(BD.Tareas.DEADLINE, formateadorFechas.format(tarea.getDeadline()));


            int completada = 0;
            if (tarea.isCompletado())
                completada = 1;
            valores.put(BD.Tareas.COMPLETADA, completada);


            baseDatos.insertOrThrow(BD.TAREAS, null, valores);

        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar la reunión", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public DTTarea buscarTarea(String descripcion, DTEvento evento) throws Exception {

        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.TAREAS, BD.Tareas.COLUMNAS, BD.Tareas.DESCRIPCION + " = ? AND " + BD.Tareas.TITULOEVENTO + " = ?", new String[]{String.valueOf(descripcion), String.valueOf(evento.getTitulo())}, null, null, null);
            DTTarea tarea = null;

            if (datos.moveToNext()) {
                tarea = (instanciaTarea(datos));

            }

            return tarea;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener la tarea.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public void modificarTarea(DTTarea tarea) throws Exception {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();

            if (buscarTarea(tarea.getDescripcion(), tarea.getEvento()) == null) {
                throw new ExcepcionPersistencia("La tarea no existe.");
            }

            ContentValues valores = new ContentValues();
            valores.put(BD.Tareas.DESCRIPCION, tarea.getDescripcion());
            valores.put(BD.Tareas.TITULOEVENTO, tarea.getEvento().getTitulo());
            valores.put(BD.Tareas.DEADLINE, formateadorFechas.format(tarea.getDeadline()));
            valores.put(BD.Tareas.COMPLETADA, tarea.isCompletado());
            baseDatos.update(BD.TAREAS, valores, BD.Tareas.DESCRIPCION + " = ?", new String[]{String.valueOf(tarea.getDescripcion())});

        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar la reunión", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public List<DTTarea> listarTareas(DTEvento evento) throws Exception {

        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.TAREAS, BD.Tareas.COLUMNAS, BD.Tareas.TITULOEVENTO + " = ?", new String[]{String.valueOf(evento.getTitulo())}, null, null, null);
            List<DTTarea> listaTarea = new ArrayList<>();

            while (datos.moveToNext()) {
                listaTarea.add(instanciaTarea(datos));

            }

            return listaTarea;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar las tareas.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    public DTTarea instanciaTarea(Cursor datos) throws Exception {
        int columnaDescripcion = datos.getColumnIndex(BD.Tareas.DESCRIPCION);
        int columnaTitulo = datos.getColumnIndex(BD.Tareas.TITULOEVENTO);
        int columnaDeadline = datos.getColumnIndex(BD.Tareas.DEADLINE);
        int columnaCompletada = datos.getColumnIndex(BD.Tareas.COMPLETADA);


        DTEvento evento = FabricaPersistencia.getPersistenciaEvento(contexto.getApplicationContext()).buscarEvento(datos.getString(columnaTitulo));
        Date fecha = formateadorFechas.parse(datos.getString(columnaDeadline));

        boolean completada;

        if (datos.getInt(columnaCompletada) == 1)
            completada = true;
        else
            completada = false;

        DTTarea tarea = new DTTarea(datos.getString(columnaDescripcion), fecha, completada, evento);

        return tarea;
    }
}
