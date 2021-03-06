package es.aplicacion.guiacastulo.db.schema;

/**
 * Constantes para la tabla de marcadores
 * Created by Enmanuel on 21/01/2015.
 */
public class ColumnasMarcadores {

    public static final String NOMBRE_TABLA = "marcadores";

    // Columnas
    public static final String KEY_ID = "_id";

    public static final String ID_PUNTOS_INTERES = "id_puntos_interes";
    public static final String NOMBRE = "nombre";
    public static final String DESCRIPCION = "descripcion";
    public static final String IMAGEN = "imagen";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String ID_SERVIDOR = "id_servidor";
    public static final String VERSION = "version";


    public static final String CREAR_TABLA = "CREATE TABLE " + NOMBRE_TABLA
            + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOMBRE + " TEXT, " + DESCRIPCION + " TEXT, "+ ID_PUNTOS_INTERES + " TEXT, "
            + IMAGEN + " TEXT, "+ VIDEO + " TEXT, "
            + AUDIO + " TEXT, "+ LATITUD + " REAL, "+ LONGITUD + " REAL, "+ID_SERVIDOR+" INTEGER, "
            +VERSION+" INTEGER );";
}
