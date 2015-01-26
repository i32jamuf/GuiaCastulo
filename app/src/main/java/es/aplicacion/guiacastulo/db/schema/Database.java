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
import es.aplicacion.guiacastulo.db.model.Informacion;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.model.Recorrido;

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
            cv.put(ColumnasPuntosInteres.AUDIO, Utils.crearStringComas(pOI.getUriAudio()));
            cv.put(ColumnasPuntosInteres.VIDEO, Utils.crearStringComas(pOI.getUriVideo()));
            cv.put(ColumnasPuntosInteres.IMAGEN, Utils.crearStringComas(pOI.getUriImagen()));

            pOI.setId(db.insert(ColumnasPuntosInteres.NOMBRE_TABLA, null, cv));
        }

        db.setTransactionSuccessful();
    } finally {
        db.endTransaction();
    }
    return true;
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
                    ColumnasPuntosInteres.AUDIO
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
            pOI.setUriVideo(Utils.separarStringComasAString(c.getString(6)));
            pOI.setUriAudio(Utils.separarStringComasAString(c.getString(7)));


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
                ColumnasPuntosInteres.AUDIO}, null, null, null, null, null);
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
                pOI.setUriVideo(Utils.separarStringComasAString(c.getString(6)));
                pOI.setUriAudio(Utils.separarStringComasAString(c.getString(7)));
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
            cv.put(ColumnasPuntosInteres.VIDEO, Utils.crearStringComas(PoI.getUriVideo()));
        if (PoI.getUriAudio() != null)
            cv.put(ColumnasPuntosInteres.AUDIO, Utils.crearStringComas(PoI.getUriAudio()));

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
     *Añade un Recorrido a la base de datos
     * @param lstRecorrido
     * @return
     */
    public boolean addRecorrido(List<Recorrido> lstRecorrido) {
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
                cv.put(ColumnasRecorridos.AUDIO, Utils.crearStringComas(recorrido.getUriAudio()));
                cv.put(ColumnasRecorridos.VIDEO, Utils.crearStringComas(recorrido.getUriVideo()));
                cv.put(ColumnasRecorridos.IMAGEN, Utils.crearStringComas(recorrido.getUriImagen()));

                recorrido.setId(db.insert(ColumnasPuntosInteres.NOMBRE_TABLA, null, cv));
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
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
                    ColumnasRecorridos.AUDIO
            }, ColumnasPuntosInteres.KEY_ID + " = " + recorrID, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto PuntoInteres
            recorrido.setId(c.getLong(0));
            recorrido.setNombre(c.getString(1));
            recorrido.setDescripcion(c.getString(2));
            recorrido.setDistancia(c.getString(3));
            recorrido.setDuracion(c.getString(4));
            //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
            recorrido.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
            recorrido.setUriVideo(Utils.separarStringComasAString(c.getString(6)));
            recorrido.setUriAudio(Utils.separarStringComasAString(c.getString(7)));


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
                    ColumnasRecorridos.AUDIO}, null, null, null, null, null);
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
                recorrido.setUriVideo(Utils.separarStringComasAString(c.getString(6)));
                recorrido.setUriAudio(Utils.separarStringComasAString(c.getString(7)));
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
                    ColumnasPuntosInteres.KEY_ID + " = " + recorridoId, null);
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
            cv.put(ColumnasRecorridos.VIDEO, Utils.crearStringComas(recorrido.getUriVideo()));
        if (recorrido.getUriAudio() != null)
            cv.put(ColumnasRecorridos.AUDIO, Utils.crearStringComas(recorrido.getUriAudio()));

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

    public boolean addMarcador(List<Marcador> lstMarcador) {
        if (lstMarcador.isEmpty())
            return false;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {
            for (Marcador marcador : lstMarcador) {
                cv.put(ColumnasMarcadores.NOMBRE, marcador.getNombre());
                cv.put(ColumnasMarcadores.DESCRIPCION, marcador.getDescripcion());
                cv.put(ColumnasMarcadores.ID_RECORRIDOS, Utils.crearStringComas(marcador.getId_recorridos()));
                cv.put(ColumnasMarcadores.ID_PUNTOS_INTERES, Utils.crearStringComas(marcador.getId_puntos_interes()));
                cv.put(ColumnasMarcadores.LATITUD, marcador.getLatitud());
                cv.put(ColumnasMarcadores.LONGITUD, marcador.getLongitud());
                //El contenido de las columnas son una sucesion de URIs separadas por comas
                cv.put(ColumnasMarcadores.IMAGEN, Utils.crearStringComas(marcador.getUriImagen()));
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
                    ColumnasMarcadores.ID_RECORRIDOS,
                    ColumnasMarcadores.ID_PUNTOS_INTERES
            }, ColumnasPuntosInteres.KEY_ID + " = " + markID, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto PuntoInteres
            marcador.setId(c.getLong(0));
            marcador.setNombre(c.getString(1));
            marcador.setDescripcion(c.getString(2));
            marcador.setLatitud(c.getDouble(3));
            marcador.setLongitud(c.getDouble(4));
            //separamos cada URI delimitada por comas y las ponemos en cada una de las posiciones del array
            marcador.setUriImagen(Utils.separarStringComasAString(c.getString(5)));
            marcador.setIdsRecorridos(Utils.separarStringComasALong(c.getString(6)));
            marcador.setIdsPoI(Utils.separarStringComasALong(c.getString(7)));


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
                    ColumnasMarcadores.ID_RECORRIDOS,
                    ColumnasMarcadores.ID_PUNTOS_INTERES}, null, null, null, null, null);
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
                marcador.setIdsRecorridos(Utils.separarStringComasALong(c.getString(6)));
                marcador.setIdsPoI(Utils.separarStringComasALong(c.getString(7)));
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
        if (marcador.getId_recorridos() != null)
            cv.put(ColumnasMarcadores.ID_RECORRIDOS, Utils.crearStringComas(marcador.getId_recorridos()));
        if (marcador.getId_puntos_interes() != null)
            cv.put(ColumnasMarcadores.ID_PUNTOS_INTERES, Utils.crearStringComas(marcador.getId_puntos_interes()));

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

    public boolean addInformacion(Informacion informacion) {
        if (informacion.equals(null))
            return false;

        ContentValues cv = new ContentValues();
        db.beginTransaction();

        try {
                cv.put(ColumnasInformacion.HORARIO, informacion.getHorario());
                cv.put(ColumnasInformacion.TELEFONO, informacion.getTelefono());
                cv.put(ColumnasInformacion.DIRECCION, informacion.getDireccion());
                cv.put(ColumnasInformacion.WEB, informacion.getWeb());
                cv.put(ColumnasInformacion.MAS_INFO, informacion.getMasInfo());
                db.insert(ColumnasMarcadores.NOMBRE_TABLA, null, cv);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return true;
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
            }, ColumnasInformacion.KEY_ID + " = " + infoID, null, null, null, null, null);

            c.moveToFirst();
            //rellenamos el objeto PuntoInteres
            informacion.setId(c.getLong(0));
            informacion.setHorario(c.getString(1));
            informacion.setTelefono(c.getString(2));
            informacion.setDireccion(c.getString(3));
            informacion.setWeb(c.getString(4));
            informacion.setMasInfo(c.getString(5));

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

}
