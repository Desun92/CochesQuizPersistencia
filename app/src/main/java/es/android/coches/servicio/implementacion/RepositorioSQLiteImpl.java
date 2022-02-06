package es.android.coches.servicio.implementacion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import es.android.coches.entidad.Pregunta;
import es.android.coches.servicio.interfaz.Repositorio;

public class RepositorioSQLiteImpl extends SQLiteOpenHelper implements Repositorio<Pregunta> {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Preguntas.db";

    public RepositorioSQLiteImpl(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + ContratoPregunta.EntradaPregunta.NOMBRE_TABLA +
                        " ("
                        + ContratoPregunta.EntradaPregunta._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + ContratoPregunta.EntradaPregunta.NOMBRE + " TEXT NOT NULL,"
                        + ContratoPregunta.EntradaPregunta.FOTO + " TEXT NOT NULL,"
                        + "UNIQUE (" + ContratoPregunta.EntradaPregunta.NOMBRE + "))");

        Pregunta pregunta1 = new Pregunta("Seat","seat.jpeg");
        this.save(pregunta1, db);

        Pregunta pregunta2 = new Pregunta("Dacia", "dacia.jpeg");
        this.save(pregunta2, db);

        Pregunta pregunta3 = new Pregunta("Mercedes", "mercedes.jpeg");
        this.save(pregunta3, db);

        Pregunta pregunta4 = new Pregunta("BMW", "bmw.jpeg");
        this.save(pregunta4, db);

        Pregunta pregunta5 = new Pregunta("Audi", "audi.jpeg");
        this.save(pregunta5, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Optional<Pregunta> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Pregunta> getAll() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                ContratoPregunta.EntradaPregunta.NOMBRE_TABLA,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Pregunta> preguntas = new LinkedList<>();
        while(c.moveToNext()){
            @SuppressLint("Range")
            String nombre = c.getString(
                    c.getColumnIndex(ContratoPregunta.EntradaPregunta.NOMBRE));
            @SuppressLint("Range")
            String foto = c.getString(
                    c.getColumnIndex(ContratoPregunta.EntradaPregunta.FOTO));
            preguntas.add(new Pregunta(nombre, foto));
        }
        return preguntas;
    }

    @Override
    public void save(Pregunta pregunta) {
        this.save(pregunta, null);
    }

    private void save(Pregunta pregunta, SQLiteDatabase db){

        if(db == null)
            db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ContratoPregunta.EntradaPregunta.NOMBRE, pregunta.getNombre());
        values.put(ContratoPregunta.EntradaPregunta.FOTO, pregunta.getFoto());

        db.insert(ContratoPregunta.EntradaPregunta.NOMBRE_TABLA, null, values);
    }

    @Override
    public void update(Pregunta pregunta) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ContratoPregunta.EntradaPregunta.NOMBRE, pregunta.getNombre());
        values.put(ContratoPregunta.EntradaPregunta.FOTO, pregunta.getFoto());

        db.update(ContratoPregunta.EntradaPregunta.NOMBRE_TABLA, values, "nombre=?", new String[] {pregunta.getNombre()});
    }

    @Override
    public void delete(Pregunta pregunta) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(ContratoPregunta.EntradaPregunta.NOMBRE_TABLA,"nombre=?",new String[] {pregunta.getNombre()});

    }

}

class ContratoPregunta{

    private ContratoPregunta(){}

    public static class EntradaPregunta implements BaseColumns{

        public static final String NOMBRE_TABLA = "Pregunta";
        public static final String NOMBRE = "Nombre";
        public static final String FOTO = "Foto";

    }
}
