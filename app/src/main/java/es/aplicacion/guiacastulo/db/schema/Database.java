package es.aplicacion.guiacastulo.db.schema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.db.model.Coordenada;
import es.aplicacion.guiacastulo.db.model.Informacion;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.model.Update;

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
            db.execSQL(ColumnasUpdates.CREAR_TABLA);
            db.execSQL(ColumnasCoordenada.CREAR_TABLA);
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
            db.execSQL("DROP TABLE IF EXISTS "+ ColumnasMarcadores.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS "+ ColumnasInformacion.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS "+ ColumnasCoordenada.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS "+ ColumnasUpdates.NOMBRE_TABLA);
            onCreate(db);
        }
    }

    /**
     * Añade una lista de puntos de interes a la DB
     * @param lstPuntosInteres
     * @return
     */
    public boolean addPuntosInteres(List<PuntoInteres> lstPuntosInteres) {
        if (lstPuntosInteres.isEmpty())
            return false;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {
            for (PuntoInteres pOI : lstPuntosInteres) {
                cv.put(ColumnasPuntosInteres.NOMBRE, pOI.getNombre());
                cv.put(ColumnasPuntosInteres.DESCRIPCION, pOI.getDescripcion());
                cv.put(ColumnasPuntosInteres.LATITUD, pOI.getLatitud());
                cv.put(ColumnasPuntosInteres.LONGITUD, pOI.getLongitud());
                //El contenido de las columnas son una sucesion de URIs separadas por comas
                cv.put(ColumnasPuntosInteres.AUDIO, (pOI.getUriAudio()));
                cv.put(ColumnasPuntosInteres.VIDEO, (pOI.getUriVideo()));
                cv.put(ColumnasPuntosInteres.IMAGEN, Utils.crearStringComas(pOI.getUriImagen()));
                cv.put(ColumnasPuntosInteres.ID_SERVIDOR, pOI.getIdServidor());
                cv.put(ColumnasPuntosInteres.VERSION, pOI.getVersion());
                pOI.setId(db.insert(ColumnasPuntosInteres.NOMBRE_TABLA, null, cv));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
    }

    /**
     * Añade un punto de interes a la DB
     * @param pOI
     * @return
     */
    public long addPuntoInteres(PuntoInteres pOI) {
        long id=-1;
        if (pOI==null)
            return id;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {

            cv.put(ColumnasPuntosInteres.NOMBRE, pOI.getNombre());
            cv.put(ColumnasPuntosInteres.DESCRIPCION, pOI.getDescripcion());
            cv.put(ColumnasPuntosInteres.LATITUD, pOI.getLatitud());
            cv.put(ColumnasPuntosInteres.LONGITUD, pOI.getLongitud());
            //El contenido de las columnas son una sucesion de URIs separadas por comas
            cv.put(ColumnasPuntosInteres.AUDIO, (pOI.getUriAudio()));
            cv.put(ColumnasPuntosInteres.VIDEO, (pOI.getUriVideo()));
            cv.put(ColumnasPuntosInteres.IMAGEN, Utils.crearStringComas(pOI.getUriImagen()));
            cv.put(ColumnasPuntosInteres.ID_SERVIDOR, pOI.getIdServidor());
            cv.put(ColumnasPuntosInteres.VERSION, pOI.getVersion());
            id=db.insert(ColumnasPuntosInteres.NOMBRE_TABLA, null, cv);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id;
    }
    /**
     * Obtenemos de la base de datos un punto de interes y lo guardamos en un objeto del mismo tipo
     * @param puntoID
     * @return {@PuntoInteres}
     */
    public PuntoInteres getPuntoInteres(long puntoID) {
        PuntoInteres pOI = new PuntoInteres();
        db.beginTransaction();
        try {
            Cursor c = db.query(ColumnasPuntosInteres.NOMBRE_TABLA, new String[] {
                    ColumnasPuntosInteres.KEY_ID,
                    ColumnasPuntosInteres.NOMBRE,
                    ColumnasPuntosInteres.DESCRIPCION,
                    ColumnasPuntosInteres.LATITUD,
                    ColumnasPuntosInteres.LONGITUD,
                    ColumnasPuntosInteres.IMAGEN,
                    ColumnasPuntosInteres.VIDEO,
                    ColumnasPuntosInteres.AUDIO,
                    ColumnasPuntosInteres.ID_SERVIDOR,
                    ColumnasPuntosInteres.VERSION
            }, ColumnasPuntosInteres.KEY_ID + " = " + puntoID, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto PuntoInteres
            pOI.setId(c.getLong(0));
            pOI.setNombre(c.getString(1));
            pOI.setDescripcion(c.getString(2));
            pOI.setLatitud(c.getDouble(3));
            pOI.setLongitud(c.getDouble(4));
            //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
            pOI.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
            pOI.setUriVideo((c.getString(6)));
            pOI.setUriAudio((c.getString(7)));
            pOI.setIdServidor(c.getLong(8));
            pOI.setVersion(c.getLong(9));

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return pOI;
    }

    /**
     * Lista todas los puntos de interes de la base de datos.
     *
     * @return Coleccion de objetos {@link es.aplicacion.guiacastulo.db.model.PuntoInteres}
     */
    public List<PuntoInteres> getAllPuntoInteres() {
        LinkedList<PuntoInteres> allPuntosInteres = new LinkedList<PuntoInteres>();

        db.beginTransaction();
        try{
            Cursor c = db.query(ColumnasPuntosInteres.NOMBRE_TABLA, new String[] {
                    ColumnasPuntosInteres.KEY_ID,
                    ColumnasPuntosInteres.NOMBRE,
                    ColumnasPuntosInteres.DESCRIPCION,
                    ColumnasPuntosInteres.LATITUD,
                    ColumnasPuntosInteres.LONGITUD,
                    ColumnasPuntosInteres.IMAGEN,
                    ColumnasPuntosInteres.VIDEO,
                    ColumnasPuntosInteres.AUDIO,
                    ColumnasPuntosInteres.ID_SERVIDOR,
                    ColumnasPuntosInteres.VERSION}, null, null, null, null, null);
            c.moveToFirst();
            c.moveToPrevious();

            while (c.moveToNext()) {
                PuntoInteres pOI = new PuntoInteres();
                //rellenamos el objeto PuntoInteres
                pOI.setId(c.getLong(0));
                pOI.setNombre(c.getString(1));
                pOI.setDescripcion(c.getString(2));
                pOI.setLatitud(c.getDouble(3));
                pOI.setLongitud(c.getDouble(4));
                //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
                pOI.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
                pOI.setUriVideo((c.getString(6)));
                pOI.setUriAudio((c.getString(7)));
                pOI.setIdServidor(c.getLong(8));
                pOI.setVersion(c.getLong(9));
                allPuntosInteres.add(pOI);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return allPuntosInteres;
    }

    /**
     * Elimina un punto de interes de la base de datos.
     *
     * @param puntoInteresId
     * @return el numero de filas afectadas.
     */
    public int deletePuntoInteres(long puntoInteresId) {
        int filas=0;
        db.beginTransaction();
        try{
            filas = db.delete(ColumnasPuntosInteres.NOMBRE_TABLA,
                    ColumnasPuntosInteres.KEY_ID + " = " + puntoInteresId, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }

    /**
     * Edita un PuntoInteres de la base de datos
     * @param PoI
     * @return
     */
    public int editPuntoInteres(PuntoInteres PoI) {

        int filas;
        ContentValues cv = new ContentValues();

        if (PoI.getNombre() != null)
            cv.put(ColumnasPuntosInteres.NOMBRE, PoI.getNombre());
        if (PoI.getDescripcion() != null)
            cv.put(ColumnasPuntosInteres.DESCRIPCION, PoI.getDescripcion());
        if (PoI.getLatitud() != 0)
            cv.put(ColumnasPuntosInteres.LATITUD, PoI.getLatitud());
        if (PoI.getLongitud() != 0)
            cv.put(ColumnasPuntosInteres.LONGITUD, PoI.getLatitud());

        //TODO posible origen de error por que los contenidos del array sean null o caracter vacio
        if (PoI.getUriImagen() != null)
            cv.put(ColumnasPuntosInteres.IMAGEN, Utils.crearStringComas(PoI.getUriImagen()));
        if (PoI.getUriVideo() != null)
            cv.put(ColumnasPuntosInteres.VIDEO, (PoI.getUriVideo()));
        if (PoI.getUriAudio() != null)
            cv.put(ColumnasPuntosInteres.AUDIO, (PoI.getUriAudio()));

        cv.put(ColumnasPuntosInteres.ID_SERVIDOR, (PoI.getIdServidor()));
        cv.put(ColumnasPuntosInteres.VERSION, (PoI.getVersion()));

        db.beginTransaction();
        try{
            filas= db.update(ColumnasPuntosInteres.NOMBRE_TABLA, cv,
                    ColumnasPuntosInteres.KEY_ID + " = " + PoI.getId(), null);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }


//TODO recorridos

    /**
     *Añade una lista de Recorridos a la base de datos
     * @param lstRecorrido
     * @return
     */
    public boolean addRecorridos(List<Recorrido> lstRecorrido) {
        if (lstRecorrido.isEmpty())
            return false;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {
            for (Recorrido recorrido : lstRecorrido) {
                cv.put(ColumnasRecorridos.NOMBRE, recorrido.getNombre());
                cv.put(ColumnasRecorridos.DESCRIPCION, recorrido.getDescripcion());
                cv.put(ColumnasRecorridos.DISTANCIA,recorrido.getDistancia());
                cv.put(ColumnasRecorridos.DURACION, recorrido.getDuracion());
                //El contenido de las columnas son una sucesion de URIs separadas por comas
                cv.put(ColumnasRecorridos.AUDIO, (recorrido.getUriAudio()));
                cv.put(ColumnasRecorridos.VIDEO, (recorrido.getUriVideo()));
                cv.put(ColumnasRecorridos.IMAGEN, Utils.crearStringComas(recorrido.getUriImagen()));
                cv.put(ColumnasRecorridos.ID_MARCADORES, Utils.crearStringComas(recorrido.getId_marcadores()));
                cv.put(ColumnasRecorridos.ID_SERVIDOR, recorrido.getIdServidor());
                cv.put(ColumnasRecorridos.VERSION, recorrido.getVersion());
                recorrido.setId(db.insert(ColumnasRecorridos.NOMBRE_TABLA, null, cv));
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
    }

    /**
     *Añade un Recorrido a la base de datos
     * @param recorrido
     * @return
     */
    public long addRecorrido(Recorrido recorrido) {
        long id=-1;
        if (recorrido==null)
            return id;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {

            cv.put(ColumnasRecorridos.NOMBRE, recorrido.getNombre());
            cv.put(ColumnasRecorridos.DESCRIPCION, recorrido.getDescripcion());
            cv.put(ColumnasRecorridos.DISTANCIA,recorrido.getDistancia());
            cv.put(ColumnasRecorridos.DURACION, recorrido.getDuracion());
            //El contenido de las columnas son una sucesion de URIs separadas por comas
            cv.put(ColumnasRecorridos.AUDIO, (recorrido.getUriAudio()));
            cv.put(ColumnasRecorridos.VIDEO, (recorrido.getUriVideo()));
            cv.put(ColumnasRecorridos.IMAGEN, Utils.crearStringComas(recorrido.getUriImagen()));
            cv.put(ColumnasRecorridos.ID_MARCADORES, Utils.crearStringComas(recorrido.getId_marcadores()));
            cv.put(ColumnasRecorridos.ID_SERVIDOR, recorrido.getIdServidor());
            cv.put(ColumnasRecorridos.VERSION, recorrido.getVersion());
            id=db.insert(ColumnasRecorridos.NOMBRE_TABLA, null, cv);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id;
    }

    /**
     *
     * @param recorrID
     * @return
     */
    public Recorrido getRecorrido(long recorrID) {
        Recorrido recorrido = new Recorrido();
        db.beginTransaction();
        try {
            Cursor c = db.query(ColumnasRecorridos.NOMBRE_TABLA, new String[] {
                    ColumnasRecorridos.KEY_ID,
                    ColumnasRecorridos.NOMBRE,
                    ColumnasRecorridos.DESCRIPCION,
                    ColumnasRecorridos.DISTANCIA,
                    ColumnasRecorridos.DURACION,
                    ColumnasRecorridos.IMAGEN,
                    ColumnasRecorridos.VIDEO,
                    ColumnasRecorridos.AUDIO,
                    ColumnasRecorridos.ID_MARCADORES,
                    ColumnasRecorridos.ID_SERVIDOR,
                    ColumnasRecorridos.VERSION
            }, ColumnasRecorridos.KEY_ID + " = " + recorrID, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto PuntoInteres
            recorrido.setId(c.getLong(0));
            recorrido.setNombre(c.getString(1));
            recorrido.setDescripcion(c.getString(2));
            recorrido.setDistancia(c.getString(3));
            recorrido.setDuracion(c.getString(4));
            //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
            recorrido.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
            recorrido.setUriVideo((c.getString(6)));
            recorrido.setUriAudio((c.getString(7)));
            recorrido.setIdsMarcadores(Utils.separarStringComasALong(c.getString(8)));
            recorrido.setIdServidor(c.getLong(9));
            recorrido.setVersion(c.getLong(10));


            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return recorrido;
    }

    /**
     * Lista todas los recorridos de la base de datos.
     *
     * @return Coleccion de objetos {@link es.aplicacion.guiacastulo.db.model.Recorrido}
     */
    public List<Recorrido> getAllRecorridos() {
        LinkedList<Recorrido> allRecorridos = new LinkedList<Recorrido>();

        db.beginTransaction();
        try{
            Cursor c = db.query(ColumnasRecorridos.NOMBRE_TABLA, new String[] {
                    ColumnasRecorridos.KEY_ID,
                    ColumnasRecorridos.NOMBRE,
                    ColumnasRecorridos.DESCRIPCION,
                    ColumnasRecorridos.DISTANCIA,
                    ColumnasRecorridos.DURACION,
                    ColumnasRecorridos.IMAGEN,
                    ColumnasRecorridos.VIDEO,
                    ColumnasRecorridos.AUDIO,
                    ColumnasRecorridos.ID_MARCADORES,
                    ColumnasRecorridos.ID_SERVIDOR,
                    ColumnasRecorridos.VERSION}, null, null, null, null, null);
            c.moveToFirst();
            c.moveToPrevious();

            while (c.moveToNext()) {
                Recorrido recorrido = new Recorrido();
                //rellenamos el objeto PuntoInteres
                recorrido.setId(c.getLong(0));
                recorrido.setNombre(c.getString(1));
                recorrido.setDescripcion(c.getString(2));
                recorrido.setDistancia(c.getString(3));
                recorrido.setDuracion(c.getString(4));
                //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
                recorrido.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
                recorrido.setUriVideo((c.getString(6)));
                recorrido.setUriAudio((c.getString(7)));
                recorrido.setIdsMarcadores(Utils.separarStringComasALong(c.getString(8)));
                recorrido.setIdServidor(c.getLong(9));
                recorrido.setVersion(c.getLong(10));
                allRecorridos.add(recorrido);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return allRecorridos;
    }

    /**
     * Elimina un recorrido de la base de datos.
     *
     * @param recorridoId
     * @return el numero de filas afectadas.
     */
    public int deleteRecorrido(long recorridoId) {
        int filas=0;
        db.beginTransaction();
        try{
            filas = db.delete(ColumnasRecorridos.NOMBRE_TABLA,
                    ColumnasRecorridos.KEY_ID + " = " + recorridoId, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }

    /**
     * Edita un Recorrido de la base de datos
     * @param recorrido
     * @return
     */
    public int editRecorrido(Recorrido recorrido) {

        int filas;
        ContentValues cv = new ContentValues();

        if (recorrido.getNombre() != null)
            cv.put(ColumnasRecorridos.NOMBRE, recorrido.getNombre());
        if (recorrido.getDescripcion() != null)
            cv.put(ColumnasRecorridos.DESCRIPCION, recorrido.getDescripcion());
        if (recorrido.getDistancia() != null)
            cv.put(ColumnasRecorridos.DISTANCIA, recorrido.getDistancia());
        if (recorrido.getDistancia() != null)
            cv.put(ColumnasRecorridos.DURACION, recorrido.getDuracion());

        //TODO posible origen de error por que los contenidos del array sean null o caracter vacio
        if (recorrido.getUriImagen() != null)
            cv.put(ColumnasRecorridos.IMAGEN, Utils.crearStringComas(recorrido.getUriImagen()));
        if (recorrido.getUriVideo() != null)
            cv.put(ColumnasRecorridos.VIDEO, (recorrido.getUriVideo()));
        if (recorrido.getUriAudio() != null)
            cv.put(ColumnasRecorridos.AUDIO, (recorrido.getUriAudio()));
        if(recorrido.getId_marcadores() != null)
            cv.put(ColumnasRecorridos.ID_MARCADORES, Utils.crearStringComas(recorrido.getId_marcadores()));

        cv.put(ColumnasRecorridos.ID_SERVIDOR, (recorrido.getIdServidor()));
        cv.put(ColumnasRecorridos.VERSION, (recorrido.getVersion()));
        db.beginTransaction();
        try{
            filas= db.update(ColumnasRecorridos.NOMBRE_TABLA, cv,
                    ColumnasRecorridos.KEY_ID + " = " + recorrido.getId(), null);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }

    //TODO marcadores

    public long addMarcador(Marcador marcador) {
        long id=-1;
        if (marcador==null)
            return id;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {

            cv.put(ColumnasMarcadores.NOMBRE, marcador.getNombre());
            cv.put(ColumnasMarcadores.DESCRIPCION, marcador.getDescripcion());
            cv.put(ColumnasMarcadores.ID_PUNTOS_INTERES, Utils.crearStringComas(marcador.getId_puntos_interes()));
            cv.put(ColumnasMarcadores.LATITUD, marcador.getLatitud());
            cv.put(ColumnasMarcadores.LONGITUD, marcador.getLongitud());
            //El contenido de las columnas son una sucesion de URIs separadas por comas
            cv.put(ColumnasMarcadores.IMAGEN, Utils.crearStringComas(marcador.getUriImagen()));
            cv.put(ColumnasMarcadores.ID_SERVIDOR, marcador.getIdServidor());
            cv.put(ColumnasMarcadores.VERSION, marcador.getVersion());
            id= db.insert(ColumnasMarcadores.NOMBRE_TABLA, null, cv);


            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id;
    }
    public boolean addMarcadores(List<Marcador> lstMarcador) {
        if (lstMarcador.isEmpty())
            return false;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {
            for (Marcador marcador : lstMarcador) {
                cv.put(ColumnasMarcadores.NOMBRE, marcador.getNombre());
                cv.put(ColumnasMarcadores.DESCRIPCION, marcador.getDescripcion());
                cv.put(ColumnasMarcadores.ID_PUNTOS_INTERES, Utils.crearStringComas(marcador.getId_puntos_interes()));
                cv.put(ColumnasMarcadores.LATITUD, marcador.getLatitud());
                cv.put(ColumnasMarcadores.LONGITUD, marcador.getLongitud());
                //El contenido de las columnas son una sucesion de URIs separadas por comas
                cv.put(ColumnasMarcadores.IMAGEN, Utils.crearStringComas(marcador.getUriImagen()));
                cv.put(ColumnasMarcadores.ID_SERVIDOR, marcador.getIdServidor());
                cv.put(ColumnasMarcadores.VERSION, marcador.getVersion());
                marcador.setId(db.insert(ColumnasMarcadores.NOMBRE_TABLA, null, cv));
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
    }
    /**
     * Obtenemos de la base de datos un punto de interes y lo guardamos en un objeto del mismo tipo
     * @param markID
     * @return {@PuntoInteres}
     */
    public Marcador getMarcador(long markID) {
        Marcador marcador = new Marcador();
        db.beginTransaction();
        try {
            Cursor c = db.query(ColumnasMarcadores.NOMBRE_TABLA, new String[] {
                    ColumnasMarcadores.KEY_ID,
                    ColumnasMarcadores.NOMBRE,
                    ColumnasMarcadores.DESCRIPCION,
                    ColumnasMarcadores.LATITUD,
                    ColumnasMarcadores.LONGITUD,
                    ColumnasMarcadores.IMAGEN,
                    ColumnasMarcadores.ID_PUNTOS_INTERES,
                    ColumnasMarcadores.ID_SERVIDOR,
                    ColumnasMarcadores.VERSION
            }, ColumnasMarcadores.KEY_ID + " = " + markID, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto Marcador
            marcador.setId(c.getLong(0));
            marcador.setNombre(c.getString(1));
            marcador.setDescripcion(c.getString(2));
            marcador.setLatitud(c.getDouble(3));
            marcador.setLongitud(c.getDouble(4));
            //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
            marcador.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
            marcador.setIdsPoI(Utils.separarStringComasALong(c.getString(6)));
            marcador.setIdServidor(c.getLong(7));
            marcador.setVersion(c.getLong(8));

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return marcador;
    }

    /**
     * Lista todas los puntos de interes de la base de datos.
     *
     * @return Coleccion de objetos {@link es.aplicacion.guiacastulo.db.model.PuntoInteres}
     */
    public List<Marcador> getAllMarcadores() {
        LinkedList<Marcador> allMarcadores = new LinkedList<Marcador>();

        db.beginTransaction();
        try{
            Cursor c = db.query(ColumnasMarcadores.NOMBRE_TABLA, new String[] {
                    ColumnasMarcadores.KEY_ID,
                    ColumnasMarcadores.NOMBRE,
                    ColumnasMarcadores.DESCRIPCION,
                    ColumnasMarcadores.LATITUD,
                    ColumnasMarcadores.LONGITUD,
                    ColumnasMarcadores.IMAGEN,
                    ColumnasMarcadores.ID_PUNTOS_INTERES,
                    ColumnasMarcadores.ID_SERVIDOR,
                    ColumnasMarcadores.VERSION}, null, null, null, null, null);
            c.moveToFirst();
            c.moveToPrevious();

            while (c.moveToNext()) {
                Marcador marcador = new Marcador();
                //rellenamos el objeto PuntoInteres
                marcador.setId(c.getLong(0));
                marcador.setNombre(c.getString(1));
                marcador.setDescripcion(c.getString(2));
                marcador.setLatitud(c.getDouble(3));
                marcador.setLongitud(c.getDouble(4));
                //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
                marcador.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
                marcador.setIdsPoI(Utils.separarStringComasALong(c.getString(6)));
                marcador.setIdServidor(c.getLong(7));
                marcador.setVersion(c.getLong(8));
                allMarcadores.add(marcador);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return allMarcadores;
    }

    /**
     * Elimina un marcador de la base de datos.
     *
     * @param marcadorId
     * @return el numero de filas afectadas.
     */
    public int deleteMarcador(long marcadorId) {
        int filas=0;
        db.beginTransaction();
        try{
            filas = db.delete(ColumnasMarcadores.NOMBRE_TABLA,
                    ColumnasMarcadores.KEY_ID + " = " + marcadorId, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }

    /**
     * Edita un Marcador de la base de datos
     * @param marcador
     * @return
     */
    public int editMarcador(Marcador marcador) {

        int filas;
        ContentValues cv = new ContentValues();

        if (marcador.getNombre() != null)
            cv.put(ColumnasMarcadores.NOMBRE, marcador.getNombre());
        if (marcador.getDescripcion() != null)
            cv.put(ColumnasMarcadores.DESCRIPCION, marcador.getDescripcion());
        if (marcador.getLatitud() != 0)
            cv.put(ColumnasMarcadores.LATITUD, marcador.getLatitud());
        if (marcador.getLongitud() != 0)
            cv.put(ColumnasMarcadores.LONGITUD, marcador.getLatitud());

        //TODO posible origen de error por que los contenidos del array sean null o caracter vacio
        if (marcador.getUriImagen() != null)
            cv.put(ColumnasMarcadores.IMAGEN, Utils.crearStringComas(marcador.getUriImagen()));
        if (marcador.getId_puntos_interes() != null)
            cv.put(ColumnasMarcadores.ID_PUNTOS_INTERES, Utils.crearStringComas(marcador.getId_puntos_interes()));

        cv.put(ColumnasMarcadores.ID_SERVIDOR, marcador.getIdServidor());
        cv.put(ColumnasMarcadores.VERSION, marcador.getVersion());

        db.beginTransaction();
        try{
            filas= db.update(ColumnasMarcadores.NOMBRE_TABLA, cv,
                    ColumnasMarcadores.KEY_ID + " = " + marcador.getId(), null);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }
    //TODO informacion

    public long addInformacion(Informacion informacion) {
        long id=-1;
        if (informacion.equals(null))
            return id;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {
            cv.put(ColumnasInformacion.HORARIO, informacion.getHorario());
            cv.put(ColumnasInformacion.TELEFONO, informacion.getTelefono());
            cv.put(ColumnasInformacion.DIRECCION, informacion.getDireccion());
            cv.put(ColumnasInformacion.WEB, informacion.getWeb());
            cv.put(ColumnasInformacion.MAS_INFO, informacion.getMasInfo());
            cv.put(ColumnasInformacion.ID_SERVIDOR, informacion.getIdServidor());
            cv.put(ColumnasInformacion.VERSION, informacion.getVersion());
            id= db.insert(ColumnasInformacion.NOMBRE_TABLA, null, cv);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id;
    }

    /**
     * Obtenemos de la base de datos la informacion de contacto
     * @param infoID
     * @return {@Informacion}
     */
    public Informacion getInformacion(long infoID) {
        Informacion informacion = new Informacion();
        db.beginTransaction();
        try {
            Cursor c = db.query(ColumnasInformacion.NOMBRE_TABLA, new String[] {
                    ColumnasInformacion.KEY_ID,
                    ColumnasInformacion.HORARIO,
                    ColumnasInformacion.TELEFONO,
                    ColumnasInformacion.DIRECCION,
                    ColumnasInformacion.WEB,
                    ColumnasInformacion.MAS_INFO,
                    ColumnasInformacion.ID_SERVIDOR,
                    ColumnasInformacion.VERSION
            }, ColumnasInformacion.KEY_ID + " = " + infoID, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto PuntoInteres
            informacion.setId(c.getLong(0));
            informacion.setHorario(c.getString(1));
            informacion.setTelefono(c.getString(2));
            informacion.setDireccion(c.getString(3));
            informacion.setWeb(c.getString(4));
            informacion.setMasInfo(c.getString(5));
            informacion.setIdServidor(c.getLong(6));
            informacion.setVersion(c.getLong(7));

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return informacion;
    }

    /**
     * Elimina la informacion de contacto de la base de datos.
     *
     * @param infoId
     * @return el numero de filas afectadas.
     */
    public int deleteInfo(long infoId) {
        int filas=0;
        db.beginTransaction();
        try{
            filas = db.delete(ColumnasInformacion.NOMBRE_TABLA,
                    ColumnasInformacion.KEY_ID + " = " + infoId, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }

    /**
     * Edita la Informacion de contacto de la base de datos
     * @param informacion
     * @return
     */
    public int editInformacion(Informacion informacion) {
        int filas;
        ContentValues cv = new ContentValues();

        if (informacion.getHorario() != null)
            cv.put(ColumnasInformacion.HORARIO, informacion.getHorario());

        if (informacion.getTelefono() != null)
            cv.put(ColumnasInformacion.TELEFONO, informacion.getTelefono());

        if (informacion.getDireccion() != null)
            cv.put(ColumnasInformacion.DIRECCION, informacion.getDireccion());

        if (informacion.getWeb() != null)
            cv.put(ColumnasInformacion.WEB, informacion.getWeb());

        if (informacion.getMasInfo() != null)
            cv.put(ColumnasInformacion.MAS_INFO, informacion.getMasInfo());

        cv.put(ColumnasInformacion.ID_SERVIDOR, informacion.getIdServidor());
        cv.put(ColumnasInformacion.VERSION, informacion.getVersion());

        db.beginTransaction();

        try{
            filas= db.update(ColumnasInformacion.NOMBRE_TABLA, cv,
                    ColumnasInformacion.KEY_ID + " = " + informacion.getId(), null);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }

    /**
     * Guarda en la base de datos una lista de coordenadas asociadas a un recorrido
     * @param coords
     * @param recorrID
     * @return
     */
    public boolean setCoords(List<Coordenada> coords, long recorrID) {

        if (coords.isEmpty())
            return false;

        ContentValues cv = new ContentValues();
        cv.put(ColumnasCoordenada.ID_RECORRIDO, recorrID);

        db.beginTransaction();
        try {
            for (Coordenada g : coords) {
                cv.put(ColumnasCoordenada.LATITUD, g.getLatitud());
                cv.put(ColumnasCoordenada.LONGITUD, g.getLongitud());

                cv.put(ColumnasCoordenada.ID_SERVIDOR, g.getIdServidor());
                cv.put(ColumnasCoordenada.VERSION, g.getVersion());
                g.setId(db.insert(ColumnasCoordenada.NOMBRE_TABLA, null, cv));
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
    }

    /**
     * Obtiene de la base de datos una lista de coordenadas asociadas a un recorrido
     * @param recorrID
     * @return
     */

    public List<Coordenada> getCoords(long recorrID) {
        LinkedList<Coordenada> coords = new LinkedList<Coordenada>();
        db.beginTransaction();
        try {
            Cursor c = db.query(ColumnasCoordenada.NOMBRE_TABLA, new String[] {
                    ColumnasCoordenada.LATITUD,
                    ColumnasCoordenada.LONGITUD,
                    ColumnasCoordenada.KEY_ID,
                    ColumnasCoordenada.ID_SERVIDOR,
                    ColumnasCoordenada.VERSION}, ColumnasCoordenada.ID_RECORRIDO
                    + " = " + recorrID , null, null, null, null, null);
            c.moveToFirst();
            c.moveToPrevious();
            // guardamos los valores segun hicimos el query
            while (c.moveToNext()) {
                Coordenada g = new Coordenada();
                g.setLatitud(c.getDouble(0));
                g.setLongitud(c.getDouble(1));
                g.setId(c.getLong(2));
                g.setIdServidor(c.getLong(3));
                g.setVersion(c.getLong(4));
                g.setIdRecorrido(recorrID);
                coords.add(g);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return coords;
    }


    /**
     * Elimina todas las coordenadas asociadas a un recorrido
     * @param recorrID
     * @return
     */
    public int deleteCoords(long recorrID) {
        int filas=0;
        db.beginTransaction();
        try {
            filas=db
                    .delete(ColumnasCoordenada.NOMBRE_TABLA,
                            ColumnasCoordenada.ID_RECORRIDO + " = " + recorrID, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }



    //TODO Updates

    public long addUpdate(Update update) {
        long id=-1;
        if (update==null)
            return id;

        ContentValues cv = new ContentValues();
        db.beginTransaction();
        try {
            cv.put(ColumnasUpdates.NOMBRE, update.getNombre());
            cv.put(ColumnasUpdates.VERSION, update.getVersion());
            id= db.insert(ColumnasUpdates.NOMBRE_TABLA, null, cv);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id;
    }
    public boolean addUpdates(List<Update> lstUpdate) {
        if (lstUpdate.isEmpty())
            return false;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {
            for (Update update : lstUpdate) {
                cv.put(ColumnasUpdates.NOMBRE, update.getNombre());
                cv.put(ColumnasUpdates.VERSION, update.getVersion());
                db.insert(ColumnasUpdates.NOMBRE_TABLA, null, cv);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
    }

    public Update getUpdate(String nombre) {
        Update update = new Update();
        db.beginTransaction();
        try {
            Cursor c = db.query(ColumnasUpdates.NOMBRE_TABLA, new String[] {
                    ColumnasUpdates.VERSION
            }, ColumnasUpdates.NOMBRE + " = " + nombre, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto Marcador
            update.setVersion(c.getLong(0));
            update.setNombre(nombre);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return update;
    }

    public List<Update> getAllUpdates() {
        LinkedList<Update> allUpdates = new LinkedList<Update>();

        db.beginTransaction();
        try{
            Cursor c = db.query(ColumnasUpdates.NOMBRE_TABLA, new String[] {
                    ColumnasUpdates.NOMBRE,
                    ColumnasUpdates.VERSION}, null, null, null, null, null);
            c.moveToFirst();
            c.moveToPrevious();

            while (c.moveToNext()) {
                Update update = new Update();
                update.setNombre(c.getString(0));
                update.setVersion(c.getLong(1));
                allUpdates.add(update);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return allUpdates;
    }

    public int deleteUpdate(String nombre) {
        int filas=0;
        db.beginTransaction();
        try{
            filas = db.delete(ColumnasUpdates.NOMBRE_TABLA,
                    ColumnasUpdates.NOMBRE + " = " + nombre, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }

    public int editUpdate(Update update) {
        int filas;
        ContentValues cv = new ContentValues();
        cv.put(ColumnasUpdates.VERSION, update.getVersion());
        db.beginTransaction();
        try{
            filas= db.update(ColumnasUpdates.NOMBRE_TABLA, cv,
                    ColumnasUpdates.NOMBRE + " = " + update.getNombre(), null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return filas;
    }
}
