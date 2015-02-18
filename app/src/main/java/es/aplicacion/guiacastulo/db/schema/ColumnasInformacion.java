package es.aplicacion.guiacastulo.db.schema;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class ColumnasInformacion {
    public static final String NOMBRE_TABLA = "informacion";

    // Columnas
    public static final String TELEFONO = "telefono";
    public static final String HORARIO = "horario";
    public static final String WEB = "web";
    public static final String DIRECCION = "direccion";
    public static final String MAS_INFO = "mas_info";
    public static final String ID_SERVIDOR = "id_servidor";
    public static final String VERSION = "version";


    public static final String CREAR_TABLA = "CREATE TABLE " + NOMBRE_TABLA
            + " (" + TELEFONO + " TEXT, " + HORARIO + " TEXT, " + WEB + " TEXT, "
            + DIRECCION + " TEXT, "+ MAS_INFO + " TEXT, "+ID_SERVIDOR+" INTEGER, "
            +VERSION+" INTEGER );";
}
