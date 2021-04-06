package com.smis935820.club;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Constructor
    public DatabaseHandler(@Nullable Context context) {
        super(context,  "CLUB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ///Por el momento deje todos los campos en condición de texto, pero el ingreso si lo defini segun su tipo de entrada...
        db.execSQL("CREATE TABLE Miembros(codMiembro TEXT PRIMARY KEY, numAfiliacion INTEGER, nombre TEXT, telefono TEXT)"); /// Creación de tabla
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Miembros");
    }

    //Metodos del CRUD

    public boolean insertData(String codMiembro, String numAfiliacion, String nombre, String telefono){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("codMiembro", codMiembro);
        contentValues.put("numAfiliacion", numAfiliacion);
        contentValues.put("nombre", nombre);
        contentValues.put("telefono", telefono);

        long result = db.insert("Miembros", null, contentValues);
        if (result==-1){
            return false;
        } else{
            return true;
        }
    }

    //obtener datos

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Miembros", null);
        return cursor;
    }

    //actualizar datos

    public boolean updateData(String codMiembro, String numAfiliacion, String nombre, String telefono){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("codMiembro", codMiembro);
        contentValues.put("numAfiliacion", numAfiliacion);
        contentValues.put("nombre", nombre);
        contentValues.put("telefono", telefono);

        //Busqueda del registro
        Cursor cursor = db.rawQuery("SELECT * FROM Miembros WHERE codMiembro=?", new String[]{codMiembro});

        //Evaluación de registro
        if (cursor.getCount()>0){
            long result = db.update("Miembros", contentValues, "codMiembro=?", new String[]{codMiembro});
            if (result==-1){
                return false;
            } else{
                return true;
            }
        } else {
            return false;
        }

    }

    //Metodo delete (Eliminar Registro)
    public boolean deleteData(String codMiembro){
        SQLiteDatabase db = this. getWritableDatabase();

        Cursor cursor = db.rawQuery( "SELECT * FROM Miembros WHERE codMiembro=?", new String[]{codMiembro});
        if (cursor.getCount()>0){
            long result = db.delete("Miembros",  "codMiembro=?", new String[]{codMiembro});
            if (result==-1){
                return false;
            } else{
                return true;
            }
        } else {
            return false;
        }
    }
}

