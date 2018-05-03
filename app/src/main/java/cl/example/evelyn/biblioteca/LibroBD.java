package cl.example.evelyn.biblioteca;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Created by Manuel on 4/25/2018.
 */

public class LibroBD extends SQLiteOpenHelper {
    private String consultaCreate ="CREATE TABLE libro(" +
            "id_libro INTEGER PRIMARY KEY AUTOINCREMENT , " +
            "titulo TEXT NOT NULL,autor   TEXT NOT NULL," +
            "editor TEXT NOT NULL,fecha TEXT NOT NULL," +
            "descripcion TEXT NOT NULL," +
            "cantidad TEXT NOT NULL," +
            "estado boolean NOT NULL default 0, " +
            "descripcionestado TEXT)";
    public LibroBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(consultaCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList listarLibros(){
        ArrayList<Libro> listar = new ArrayList<>();
        SQLiteDatabase sql = this.getWritableDatabase();
        //sql.delete("libro",null,null);

        String consulta = "SELECT * FROM libro";
        Cursor lista = sql.rawQuery(consulta,null);
        if(lista.moveToFirst()) {
            do {
                listar.add(new Libro(
                        lista.getString(0),
                        lista.getString(1),
                        lista.getString(2),
                        lista.getString(3),
                        lista.getString(4),
                        lista.getString(5),
                        lista.getString(6),
                        Boolean.parseBoolean(lista.getString(7)),
                        lista.getString(8) ) );
            }while(lista.moveToNext());

        }
        return listar;
    }
    public ArrayList listarPorTitulo(String titulo){
        ArrayList<Libro> listar = new ArrayList<>();
        SQLiteDatabase sql = this.getWritableDatabase();

        String consulta = "SELECT * FROM libro WHERE titulo like '%"+ titulo + "%'";
        Cursor lista = sql.rawQuery(consulta,null);
        if(lista.moveToFirst()) {
            do {
                listar.add(new Libro(
                        lista.getString(0),
                        lista.getString(1),
                        lista.getString(2),
                        lista.getString(3),
                        lista.getString(4),
                        lista.getString(5),
                        lista.getString(6),
                        Boolean.parseBoolean(lista.getString(7)),
                        lista.getString(8) ) );
            }while(lista.moveToNext());

        }
        return  listar;
    }
    public ArrayList listarAutor(String autor){
        ArrayList<Libro> listar = new ArrayList<>();
        SQLiteDatabase sql = this.getWritableDatabase();

        String consulta = "SELECT * FROM libro WHERE autor like '%"+ autor + "%'";
        Cursor lista = sql.rawQuery(consulta,null);
        if(lista.moveToFirst()) {
            do {
                listar.add(new Libro( lista.getString(0), lista.getString(1), lista.getString(2), lista.getString(3), lista.getString(4), lista.getString(5), lista.getString(6),Boolean.parseBoolean(lista.getString(7)),lista.getString(8) ) );
            }while(lista.moveToNext());

        }
        return  listar;
    }
    public ArrayList listarFecha(String fecha){
        ArrayList<Libro> listar = new ArrayList<>();
        SQLiteDatabase sql = this.getWritableDatabase();

        String consulta = "SELECT * FROM libro WHERE fecha like '%"+ fecha + "%'";
        Cursor lista = sql.rawQuery(consulta,null);
        if(lista.moveToFirst()) {
            do {
                listar.add(new Libro( lista.getString(0), lista.getString(1), lista.getString(2), lista.getString(3), lista.getString(4), lista.getString(5), lista.getString(6),Boolean.parseBoolean(lista.getString(7)),lista.getString(8) ) );
            }while(lista.moveToNext());

        }
        return  listar;
    }
    public String guardarLibro(Libro libro)
    {
        String mensaje;
        ArrayList<Libro> listar = new ArrayList<>();
        SQLiteDatabase sql = this.getWritableDatabase();
        String consulta = "SELECT * FROM libro WHERE titulo = '"+ libro.getTitulo().replace("'","_") + "'";
        Cursor lista = sql.rawQuery(consulta,null);
        if(lista.moveToFirst()) {
            do {
                listar.add(new Libro(
                        lista.getString(0),
                        lista.getString(1),
                        lista.getString(2),
                        lista.getString(3),
                        lista.getString(4),
                        lista.getString(5),
                        lista.getString(6),
                        Boolean.parseBoolean(lista.getString(7)),
                        lista.getString(8) ) );
            }while(lista.moveToNext());
        }
        if(listar.size()>0)
        {
            mensaje="El libro ya existe";
        }
        else {
            String consultaInsert = "INSERT INTO Libro (titulo,autor,editor,fecha,descripcion,cantidad,estado,descripcionestado)VALUES('" +
                    libro.getTitulo().replace("'","_") + "','" +
                    libro.getAutor().replace("'","_") + "','" +
                    libro.getEditor() + "','" + libro.getFechaPublicacion() + "','"+
                    libro.getDescripcion().replace("'","_") + "','" +
                    libro.getPaginas() + "','"+
                    libro.getEstado()+ "','"+
                    libro.getDescripcionestado() + "')";

            try {
                sql.execSQL(consultaInsert);
                mensaje= "Libro ingresado";

            }catch (Exception e){
                mensaje= "Libro No ingresado "+ e.getMessage();
            }

        }
        return mensaje;

    }
    public String actualizarEstado(Boolean estado,String descripcionestado,String id_libro )
    {
        String mensaje;
        SQLiteDatabase sql = this.getWritableDatabase();
        try  {

            String consultaUpdate = "UPDATE Libro SET " +
                    "estado ='" + estado + "' ," +
                    "descripcionestado = '"+ descripcionestado + "' " +
                    "WHERE id_libro =" + id_libro;

            sql.execSQL(consultaUpdate);
            mensaje= "Estado Actualizado correctamente";
        }catch (Exception e){
            mensaje= "Error actualizando estado " +e.getMessage() ;
        }
        return mensaje;

    }
}
