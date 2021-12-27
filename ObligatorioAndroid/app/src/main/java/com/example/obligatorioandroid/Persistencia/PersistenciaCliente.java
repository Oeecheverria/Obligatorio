package com.example.obligatorioandroid.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTComercial;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTParticular;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersistencia;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionPersonalizada;
import com.example.obligatorioandroid.Persistencia.Interfaces.IPersistenciaCliente;

import java.util.ArrayList;
import java.util.List;

class PersistenciaCliente implements IPersistenciaCliente {

    private static PersistenciaCliente instancia;


    public static PersistenciaCliente getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PersistenciaCliente(contexto);
        }

        return instancia;
    }

    private Context contexto;

    private PersistenciaCliente(Context contexto) {
        this.contexto = contexto.getApplicationContext();

    }

    @Override
    public List<DTCliente> listarClientes() throws ExcepcionPersonalizada {
        List<DTCliente> listaClientes = new ArrayList<>();
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datosPadre = null;
        Cursor datosHijo = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datosPadre = baseDatos.query(BD.CLIENTES, BD.Clientes.COLUMNAS, null, null, null, null, BD.Clientes._ID);

            while (datosPadre.moveToNext()) {
                int columnaID = datosPadre.getColumnIndex(BD.Clientes._ID);
                int id = datosPadre.getInt(columnaID);
                listaClientes.add(buscarCliente(id));

            }
            return listaClientes;

        } catch (
                Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar a los clientes", ex);
        } finally {
            if (datosPadre != null) datosPadre.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public DTCliente buscarCliente(int id) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;
        Cursor datosPadre = null;
        Cursor datosHijo = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getReadableDatabase();
            datosPadre = baseDatos.query(BD.CLIENTES, BD.Clientes.COLUMNAS, BD.Clientes._ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            DTCliente cliente = null;

            if (datosPadre.moveToNext()) {
                datosHijo = baseDatos.query(BD.PARTICULARES, BD.Particulares.COLUMNAS, BD.Particulares.IDCLIENTE + " = ?", new String[]{String.valueOf(id)}, null, null, null);
                if (datosHijo.moveToNext())
                    cliente = (instanciarClienteParticular(datosPadre, datosHijo));
                else {
                    datosHijo = baseDatos.query(BD.COMERCIALES, BD.Comerciales.COLUMNAS, BD.Comerciales.IDCLIENTE + " = ?", new String[]{String.valueOf(id)}, null, null, null);
                    if (datosHijo.moveToNext()) {
                        cliente = (instanciarClienteComercial(datosPadre, datosHijo));
                    }
                }
            }

            return cliente;

        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener el cliente.", ex);
        } finally {
            if (datosHijo != null) datosHijo.close();
            if (datosPadre != null) datosPadre.close();
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }


    @Override
    public void agregarCliente(DTCliente cliente) throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;

        try {
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();


            if (buscarCliente(cliente.getId()) != null) {
                throw new ExcepcionPersistencia("El cliente ya existe.");
            }

            ContentValues valoresCliente = new ContentValues();
            valoresCliente.put(BD.Clientes.DIRECCION, cliente.getDireccion());
            valoresCliente.put(BD.Clientes.TELEFONO, cliente.getTelefono());
            valoresCliente.put(BD.Clientes.EMAIL, cliente.getEmail());


            long id = baseDatos.insertOrThrow(BD.CLIENTES, null, valoresCliente);
            cliente.setId((int) id);

            ContentValues valoresHijo = new ContentValues();

            if (cliente instanceof DTParticular) {
                valoresHijo.put(BD.Particulares.CEDULA, ((DTParticular) cliente).getCedula());
                valoresHijo.put(BD.Particulares.IDCLIENTE, (cliente).getId());
                valoresHijo.put(BD.Particulares.NOMBRECOMPLETO, ((DTParticular) cliente).getNombreCompleto());
                baseDatos.insertOrThrow(BD.PARTICULARES, null, valoresHijo);
            } else {
                valoresHijo.put(BD.Comerciales.RUT, ((DTComercial) cliente).getRut());
                valoresHijo.put(BD.Comerciales.IDCLIENTE, (cliente).getId());
                valoresHijo.put(BD.Comerciales.RAZONSOCIAL, ((DTComercial) cliente).getRazonSocial());
                baseDatos.insertOrThrow(BD.COMERCIALES, null, valoresHijo);
            }

        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar el empleado.", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    @Override
    public void modificarCliente(DTCliente cliente) throws ExcepcionPersonalizada {

        {
            BDHelper bdHelper = null;
            SQLiteDatabase baseDatos = null;

            try {
                bdHelper = new BDHelper(contexto);
                baseDatos = bdHelper.getWritableDatabase();


                if (buscarCliente(cliente.getId()) == null) {
                    throw new ExcepcionPersistencia("El cliente no existe.");
                }

                ContentValues valoresCliente = new ContentValues();
                valoresCliente.put(BD.Clientes.DIRECCION, cliente.getDireccion());
                valoresCliente.put(BD.Clientes.TELEFONO, cliente.getTelefono());
                valoresCliente.put(BD.Clientes.EMAIL, cliente.getEmail());

                baseDatos.update(BD.CLIENTES, valoresCliente, BD.Clientes._ID + " = ?", new String[]{String.valueOf(cliente.getId())});

                ContentValues valoresHijo = new ContentValues();

                if (cliente instanceof DTParticular) {
                    valoresHijo.put(BD.Particulares.NOMBRECOMPLETO, ((DTParticular) cliente).getNombreCompleto());
                    baseDatos.update(BD.PARTICULARES, valoresHijo, BD.Particulares.IDCLIENTE + " = ?", new String[]{String.valueOf(cliente.getId())});
                } else {
                    valoresHijo.put(BD.Comerciales.RAZONSOCIAL, ((DTComercial) cliente).getRazonSocial());
                    baseDatos.update(BD.COMERCIALES, valoresHijo, BD.Comerciales.IDCLIENTE + " = ?", new String[]{String.valueOf(cliente.getId())});
                }

            } catch (ExcepcionPersistencia ex) {
                throw ex;
            } catch (Exception ex) {
                throw new ExcepcionPersistencia("No se pudo modificar el cliente");
            } finally {
                if (baseDatos != null) baseDatos.close();
                if (bdHelper != null) bdHelper.close();
            }
        }

    }

    @Override
    public void eliminarCliente(DTCliente cliente)
            throws ExcepcionPersonalizada {
        BDHelper bdHelper = null;
        SQLiteDatabase baseDatos = null;


        try {
            if (buscarCliente(cliente.getId()) == null) {
                throw new ExcepcionPersistencia("El cliente no existe.");
            }
            bdHelper = new BDHelper(contexto);
            baseDatos = bdHelper.getWritableDatabase();
            if (cliente instanceof DTParticular)
                baseDatos.delete(BD.PARTICULARES, BD.Particulares.IDCLIENTE + " = ?", new String[]{String.valueOf(cliente.getId())});
            else
                baseDatos.delete(BD.COMERCIALES, BD.Comerciales.IDCLIENTE + " = ?", new String[]{String.valueOf(cliente.getId())});

            baseDatos.delete(BD.CLIENTES, BD.Clientes._ID + " = ?", new String[]{String.valueOf(cliente.getId())});


        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo eliminar el cliente.", ex);
        } finally {
            if (baseDatos != null) baseDatos.close();
            if (bdHelper != null) bdHelper.close();
        }
    }

    public DTParticular instanciarClienteParticular(Cursor datosPadre, Cursor datosHijo) {
        int columnaCedula = datosHijo.getColumnIndex(BD.Particulares.CEDULA);
        int columnaNombre = datosHijo.getColumnIndex(BD.Particulares.NOMBRECOMPLETO);
        int columnaID = datosPadre.getColumnIndex(BD.Clientes._ID);
        int columnaDireccion = datosPadre.getColumnIndex(BD.Clientes.DIRECCION);
        int columnaTelefono = datosPadre.getColumnIndex(BD.Clientes.TELEFONO);
        int columnaEmail = datosPadre.getColumnIndex(BD.Clientes.EMAIL);


        DTParticular clienteParticular = new DTParticular(datosPadre.getInt(columnaID), datosPadre.getString(columnaDireccion), datosPadre.getString(columnaTelefono), datosPadre.getString(columnaEmail), datosHijo.getString(columnaCedula), datosHijo.getString(columnaNombre));

        return clienteParticular;
    }

    public DTComercial instanciarClienteComercial(Cursor datosPadre, Cursor datosHijo) {
        int columnaRUT = datosHijo.getColumnIndex(BD.Comerciales.RUT);
        int columnaRazonSocial = datosHijo.getColumnIndex(BD.Comerciales.RAZONSOCIAL);
        int columnaID = datosPadre.getColumnIndex(BD.Clientes._ID);
        int columnaDireccion = datosPadre.getColumnIndex(BD.Clientes.DIRECCION);
        int columnaTelefono = datosPadre.getColumnIndex(BD.Clientes.TELEFONO);
        int columnaEmail = datosPadre.getColumnIndex(BD.Clientes.EMAIL);

        int id = datosPadre.getInt(columnaID);
        String direccion = datosPadre.getString(columnaDireccion);
        String telefono = datosPadre.getString(columnaTelefono);
        String email = datosPadre.getString(columnaEmail);

        String razon = datosHijo.getString(columnaRazonSocial);
        String rut = datosHijo.getString(columnaRUT);


        DTComercial clienteComercial = new DTComercial(datosPadre.getInt(columnaID), datosPadre.getString(columnaDireccion), datosPadre.getString(columnaTelefono), datosPadre.getString(columnaEmail), datosHijo.getString(columnaRUT), datosHijo.getString(columnaRazonSocial));

        return clienteComercial;
    }
}
