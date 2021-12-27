package com.example.obligatorioandroid.Logica.ClasesDeTrabajo;

import com.example.obligatorioandroid.Compartidos.DataTypes.DTCliente;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTComercial;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTEvento;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTGasto;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTParticular;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTReunion;
import com.example.obligatorioandroid.Compartidos.DataTypes.DTTarea;
import com.example.obligatorioandroid.Compartidos.Excepciones.ExcepcionLogica;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class ValidadorDT {

    public static void validarEvento(DTEvento evento) throws ExcepcionLogica {

        if (evento.getTitulo().isEmpty() || evento.getTitulo() == null)
            throw new ExcepcionLogica("El titulo del evento no puede estar vacio");
        if (evento.getFecha().before(Calendar.getInstance().getTime()))
            throw new ExcepcionLogica("La fecha del evento no puede ser anterior a la fecha de hoy");
        if (evento.getDuracion() < 1)
            throw new ExcepcionLogica("La duracion del evento no puede ser menor a 1");
        if (evento.getTipo().isEmpty() || evento.getTipo() == null)
            throw new ExcepcionLogica("El tipo del evento no puede estar vacio");
        if (!(evento.getTipo().toUpperCase().trim().equalsIgnoreCase("Familiar")) && (!(evento.getTipo().toUpperCase().trim().equalsIgnoreCase("Empresarial"))) && (!(evento.getTipo().toUpperCase().trim().equalsIgnoreCase("Deportivo"))) && (!(evento.getTipo().toUpperCase().trim().equalsIgnoreCase("Social"))) && (!(evento.getTipo().toUpperCase().trim().equalsIgnoreCase("Politico"))))
            throw new ExcepcionLogica("El evento solo puede ser de tipo Familiar, Empresarial, Deportivo, Social o Politico");
        if (evento.getCantidadDeAsistentes() < 1)
            throw new ExcepcionLogica("Debe haber al menos un asistente al evento");
        if (evento.getCliente() == null)
            throw new ExcepcionLogica("El evento debe contener la informacion del cliente");

    }

    public static void validarCliente(DTCliente cliente) throws ExcepcionLogica {
        if (cliente.getDireccion().isEmpty() || cliente.getDireccion() == null)
            throw new ExcepcionLogica("La direccion del cliente no puede estar vacía");
        if (cliente.getTelefono().isEmpty() || cliente.getTelefono() == null)
            throw new ExcepcionLogica("El telefono del cliente no puede estar vacío");
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (pattern.matcher(cliente.getTelefono()).matches() == false)
            throw new ExcepcionLogica("El telefono del cliente solo puede contener números");
        if (cliente.getTelefono().length() != 8)
            throw new ExcepcionLogica("El telefono del cliente solo puede contener 8 digitos excluyendo el 0");
        if ("0".equals(cliente.getTelefono().charAt(0)))
            throw new ExcepcionLogica("Por favor no incluya el 0 en el telefono del cliente");
        if (cliente.getEmail().isEmpty() || cliente.getEmail() == null)
            throw new ExcepcionLogica("El email del cliente no puede estar vacio");
        pattern = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        if (pattern.matcher(cliente.getEmail()).matches() == false)
            throw new ExcepcionLogica("Por favor introduzca el mail del cliente con formato xxx@xxx.xxx");

        if (cliente instanceof DTParticular) {
            if (((DTParticular) cliente).getCedula().isEmpty() || ((DTParticular) cliente).getCedula() == null)
                throw new ExcepcionLogica("La cedula del cliente no puede estar vacia");
            if (((DTParticular) cliente).getNombreCompleto().isEmpty() || ((DTParticular) cliente).getNombreCompleto() == null)
                throw new ExcepcionLogica("El nombre del cliente no puede estar vacio");
        } else {
            if (((DTComercial) cliente).getRut().isEmpty() || ((DTComercial) cliente).getRut() == null)
                throw new ExcepcionLogica("El RUT del cliente no puede estar vacio");
            if (((DTComercial) cliente).getRut().length() != 12)
                throw new ExcepcionLogica("El RUT del cliente debe contener 12 digitos");
            if (((DTComercial) cliente).getRazonSocial().isEmpty() || ((DTComercial) cliente).getRazonSocial() == null)
                throw new ExcepcionLogica("La razon social del cliente no puede estar vacia");
        }
    }


    public static void validarGasto(DTGasto gasto) throws ExcepcionLogica {
        if (gasto.getMotivo().isEmpty() || gasto.getMotivo() == null)
            throw new ExcepcionLogica("El motivo del gasto no puede estar vacio");
        if (gasto.getProveedor().isEmpty() || gasto.getProveedor() == null)
            throw new ExcepcionLogica("El proveedor del gasto no puede estar vacio");
        if (gasto.getMonto() < 1)
            throw new ExcepcionLogica("El monto del gasto no puede ser 0 ");
        if (gasto.getEvento() == null)
            throw new ExcepcionLogica("La informacion del evento no puede estar vacio ");

    }

    public static void validarReunion(DTReunion reunion) throws ExcepcionLogica {
        if (reunion.getDescripcion().isEmpty() || reunion.getDescripcion() == null)
            throw new ExcepcionLogica("La descripcion de la reunion no puede estar vacia");
        if (reunion.getObjetivo().isEmpty() || reunion.getObjetivo() == null)
            throw new ExcepcionLogica("El objetivo de la reunion no puede estar vacia");
        if (reunion.getFechaYHora().before(Calendar.getInstance().getTime()))
            throw new ExcepcionLogica("La fecha de la reunion no puede ser anterior a la fecha de hoy");
        if (reunion.getLugar().isEmpty() || reunion.getLugar() == null)
            throw new ExcepcionLogica("El lugar  de la reunion no puede estar vacia");
        if (reunion.getEvento() == null)
            throw new ExcepcionLogica("La informacion del evento no puede estar vacio ");
    }

    public static void validarTarea(DTTarea tarea) throws ExcepcionLogica {
        if (tarea.getDescripcion().isEmpty() || tarea.getDescripcion() == null)
            throw new ExcepcionLogica("La descripcion de la tarea no puede estar vacia");
        if (tarea.getDeadline().before(Calendar.getInstance().getTime()))
            throw new ExcepcionLogica("La fecha limite de la reunion no puede ser anterior a la fecha de hoy");
        if (tarea.getEvento() == null)
            throw new ExcepcionLogica("La informacion del evento no puede estar vacio ");
    }
}
