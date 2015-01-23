package es.aplicacion.guiacastulo.db.schema;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class ColumnasPuntosInteres {
    public static final String NOMBRE_TABLA = "puntos_interes";

    // Columnas
    public static final String KEY_ID = "_id";
    public static final String NOMBRE = "nombre";
    public static final String DESCRIPCION = "descripcion";
    public static final String IMAGEN = "imagen";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";



    public static final String CREAR_TABLA = "CREATE TABLE " + NOMBRE_TABLA
            + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOMBRE + " TEXT, " + DESCRIPCION + " TEXT, " + VIDEO + " TEXT, "
            + AUDIO + " TEXT, "+ IMAGEN + " TEXT, "+ LATITUD + " REAL, "
            + LONGITUD + " REAL "+ ");";
}
