package es.aplicacion.guiacastulo.db.schema;

/**
 * Created by Enmanuel on 10/02/2015.
 */
public class ColumnasUpdates {
    public static final String NOMBRE_TABLA = "updates";

    // Columnas

    public static final String NOMBRE = "nombre";
    public static final String VERSION = "version";



    public static final String CREAR_TABLA = "CREATE TABLE " + NOMBRE_TABLA
            + " (" + NOMBRE + " TEXT, " + VERSION + " TEXT"+ ");";
}
