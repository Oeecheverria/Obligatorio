package com.example.obligatorioandroid.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersistencia;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaGasto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistenciaGasto implements IPersistenciaGasto {

    private static PersistenciaGasto instancia;


    public static PersistenciaGasto getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PersistenciaGasto(contexto);
        }

        return instancia;
    }

    private Context contexto;


    private PersistenciaGasto(Context contexto) {
        this.contexto = contexto.getApplicationContext();


    }

    @Override
    public DTGasto buscarGasto(String motivo, DTEvento evento) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.GASTOS, BD.Gastos.COLUMNAS, BD.Gastos.MOTIVO + " = ? AND " + BD.Gastos.TITULOEVENTO + " = ?" , new String[]{String.valueOf(motivo), String.valueOf(evento.getTitulo())}, null, null, null);
            DTGasto gasto = null;

            if (datos.moveToNext()) {
                gasto = (instanciarGasto(datos));

            }

            return gasto;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener el gasto.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public List<DTGasto> listarGastos(DTEvento evento) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datos = null;
        List<DTGasto> listaGastos = new ArrayList<>();

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datos = baseDatos.query(BD.GASTOS, BD.Gastos.COLUMNAS, BD.Gastos.TITULOEVENTO + " = ?", new String[]{String.valueOf(evento.getTitulo())}, null, null, null);

            while (datos.moveToNext()) {
                listaGastos.add(instanciarGasto(datos));
            }

            return listaGastos;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener el gasto.", ex);
        } finally {
            if (datos != null) datos.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }

    }


    @Override
    public void crearGasto(DTGasto gasto) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();

            if (buscarGasto(gasto.getMotivo(), gasto.getEvento()) != null) {
                throw new ExcepcionPersistencia("El gasto ya existe.");
            }

            ContentValues valores = new ContentValues();
            valores.put(BD.Gastos.MOTIVO, gasto.getMotivo());
            valores.put(BD.Gastos.TITULOEVENTO, gasto.getEvento().getTitulo());
            valores.put(BD.Gastos.PROVEEDOR, gasto.getProveedor());
            valores.put(BD.Gastos.MONTO, gasto.getMonto());


            baseDatos.insertOrThrow(BD.GASTOS, null, valores);

        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar el evento", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }

    }

    public DTGasto instanciarGasto(Cursor datos) throws Exception {
        int columnaMotivo = datos.getColumnIndex(BD.Gastos.MOTIVO);
        int columnaTitulo = datos.getColumnIndex(BD.Gastos.TITULOEVENTO);
        int columnaProveedor = datos.getColumnIndex(BD.Gastos.PROVEEDOR);
        int columnaMonto = datos.getColumnIndex(BD.Gastos.MONTO);


        DTEvento evento = FabricaPersistencia.getPersistenciaEvento(contexto.getApplicationContext()).buscarEvento(datos.getString(columnaTitulo));

        DTGasto gasto = new DTGasto(datos.getString(columnaMotivo), datos.getString(columnaProveedor), datos.getDouble(columnaMonto), evento);

        return gasto;
    }
}
