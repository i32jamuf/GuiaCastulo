package es.aplicacion.guiacastulo.db.schema;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class ColumnasRecorridos {
    public static final String NOMBRE_TABLA = "recorridos";

    // Columnas
    public static final String KEY_ID = "_id";
    public static final String NOMBRE = "nombre";
    public static final String DESCRIPCION = "descripcion";
    public static final String IMAGEN = "imagen";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String DURACION = "tiempo";
    public static final String DISTANCIA = "distancia";



    public static final String CREAR_TABLA = "CREATE TABLE " + NOMBRE_TABLA
            + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOMBRE + " TEXT, " + DESCRIPCION + " TEXT, " + IMAGEN + " TEXT, "+ VIDEO + " TEXT, "
            + AUDIO + " TEXT, " + DURACION + " REAL, "+ DISTANCIA + " REAL "+ ");";
}
