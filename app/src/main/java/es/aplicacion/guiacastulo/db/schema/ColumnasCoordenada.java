package es.aplicacion.guiacastulo.db.schema;

/**
 * Created by Enmanuel on 04/02/2015.
 */
public class ColumnasCoordenada {
    public static final String NOMBRE_TABLA = "coordenadas";

    // Columnas
    public static final String KEY_ID = "_id"; // ID de la coordenada
    public static final String ID_RECORRIDO = "id_recorrido";
    public static final String LONGITUD = "longitud"; // Longitud
    public static final String LATITUD = "latitud"; // Latitud
    public static final String ID_SERVIDOR = "id_servidor";
    public static final String VERSION = "version";

    public static final String CREAR_TABLA = "CREATE TABLE "
            + NOMBRE_TABLA
            + " (" // tabla
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // ID de la coordenada
            + ID_RECORRIDO + " INTEGER, " // ID del rastreador
            + LATITUD + " REAL, "
            + LONGITUD + " REAL, "
            +ID_SERVIDOR+" INTEGER, "
            +VERSION+" INTEGER );";
    // time
}