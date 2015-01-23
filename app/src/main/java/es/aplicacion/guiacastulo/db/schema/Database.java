package es.aplicacion.guiacastulo.db.schema;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class Database {
    private static final String DATABASE_NAME = "Castulo_DB.db";//
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    private final Context context;
    private final DatabaseHelper dbHelper;

    /**
     * Base de datos {@link SQLiteDatabase}
     *
     * @param ctx
     */
    public Database(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Abre la base de datos. Si no puede ser abierta en modo escritura lanza
     * una excepcion y abre en modo lectura.
     *
     * @throws {@link android.database.SQLException}
     */
    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            Log.v("Open database exception caught", ex.getMessage());
            db = dbHelper.getReadableDatabase();
        }
    }

    /**
     * Cierra la base de datos.
     */

    public void close() {
        dbHelper.close();
    }

    /**
     * Database helper para la creacion y actualizacion de la base de datos.
     */

    public static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ColumnasMarcadores.CREAR_TABLA);
            db.execSQL(ColumnasPuntosInteres.CREAR_TABLA);
            db.execSQL(ColumnasRecorridos.CREAR_TABLA);
            db.execSQL(ColumnasInformacion.CREAR_TABLA);

        }

        /**
         * Actualiza la version de la base de datos. Elimina todas las tablas de
         * la version anterior.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DatabaseHelper", "Actualizando la base de datos de la version "
                    + oldVersion + " a la " + newVersion
                    + ", lo que destruira todos los datos antiguos.");

            db.execSQL("DROP TABLE IF EXISTS " + ColumnasPuntosInteres.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + ColumnasRecorridos.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS "
                    + ColumnasMarcadores.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS "
                    + ColumnasInformacion.NOMBRE_TABLA);
            onCreate(db);
        }
    }
}
