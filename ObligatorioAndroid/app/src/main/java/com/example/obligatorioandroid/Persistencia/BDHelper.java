package com.example.obligatorioandroid.Persistencia;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BDHelper extends SQLiteOpenHelper {

    private Context contexto;

    public BDHelper(Context contexto) {
        super(contexto, BD.NOMBRE_BASE_DATOS, null, BD.VERSION_BASE_DATOS);

        this.contexto = contexto;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BD.Eventos.SQL_CREAR_TABLA);
        db.execSQL(BD.Tareas.SQL_CREAR_TABLA);
        db.execSQL(BD.Reuniones.SQL_CREAR_TABLA);
        db.execSQL(BD.Gastos.SQL_CREAR_TABLA);
        db.execSQL(BD.Clientes.SQL_CREAR_TABLA);
        db.execSQL(BD.Particulares.SQL_CREAR_TABLA);
        db.execSQL(BD.Comerciales.SQL_CREAR_TABLA);

        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.CLIENTES).append(" VALUES (NULL, 'Centro','093872444', 'oscar@gmail.com'); ").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.PARTICULARES).append(" VALUES ('63648609', 1, 'Oscar');").toString());

        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.CLIENTES).append(" VALUES (NULL, 'Malvin','093872449', 'Enrique@gmail.com'); ").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.COMERCIALES).append(" VALUES ('123456781234', 2, 'Oscar Company');").toString());

        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.CLIENTES).append(" VALUES (NULL, 'Buceo','093872445', 'cheva@gmail.com'); ").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.PARTICULARES).append(" VALUES ('63396826', 3, 'Enrique');").toString());

        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.CLIENTES).append(" VALUES (NULL, 'Pocitos', '093872446','Enrique@gmail.com'); ").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.COMERCIALES).append(" VALUES ('987654321432', 4, 'Enrique Society');").toString());

        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.EVENTOS).append(" VALUES ('Bachata', '2022-03-12 00:00:00', 1, 20, 'Empresarial', 10);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.EVENTOS).append(" VALUES ('Salsa', '2022-04-12 00:00:00', 2, 30, 'Familiar', 15);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.EVENTOS).append(" VALUES ('Merengue', '2022-05-12 00:00:00', 3, 6, 'Deportivo', 100);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.EVENTOS).append(" VALUES ('Reggae', '2022-06-14 00:00:00', 3, 6, 'Social', 1000);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.EVENTOS).append(" VALUES ('Cumbia', '2022-06-14 00:00:00', 3, 6, 'Politico', 3000);").toString());


        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.REUNIONES).append(" VALUES ('Coreografia 1', 'Salsa', 'Aprender a bailar 1', '2021-06-12 00:00:00', 'Caracas', 1);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.REUNIONES).append(" VALUES ('Coreografia 2', 'Salsa', 'Aprender a bailar 2', '2022-07-12 00:00:00', 'Montevideo', 0);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.REUNIONES).append(" VALUES ('Coreografia 3', 'Salsa', 'Aprender a bailar 3', '2021-07-12 00:00:00', 'Montevideo', 0);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.REUNIONES).append(" VALUES ('Coreografia 4', 'Salsa', 'Aprender a bailar 4', '2022-07-12 00:00:00', 'Montevideo', 0);").toString());

        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.TAREAS).append(" VALUES ('Charla', 'Salsa', '2022-06-12 00:00:00', 1);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.TAREAS).append(" VALUES ('Pasos basicos', 'Salsa', '2022-07-12 00:00:00', 0);").toString());

        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.GASTOS).append(" VALUES ('Profesor 1', 'Salsa', 'Bailarin.com', 1000);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BD.GASTOS).append(" VALUES ('Profesor 2', 'Salsa', 'Bailarin.com', 3000);").toString());



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BD.Eventos.SQL_ELIMINAR_TABLA);
        db.execSQL(BD.Tareas.SQL_ELIMINAR_TABLA);
        db.execSQL(BD.Reuniones.SQL_ELIMINAR_TABLA);
        db.execSQL(BD.Gastos.SQL_ELIMINAR_TABLA);
        db.execSQL(BD.Clientes.SQL_ELIMINAR_TABLA);
        db.execSQL(BD.Particulares.SQL_ELIMINAR_TABLA);
        db.execSQL(BD.Comerciales.SQL_ELIMINAR_TABLA);

        onCreate(db);
    }
}
