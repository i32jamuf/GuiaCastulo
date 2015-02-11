package es.aplicacion.guiacastulo.db.model;

/**
 * Created by Enmanuel on 10/02/2015.
 */
public class Update {
    private long version_tabla;
    private String nombre_tabla;

    // setters


    public void setVersion(long version_tabla) {
        this.version_tabla = version_tabla;
    }

    public void setNombre(String nombre_tabla) {
        this.nombre_tabla = nombre_tabla;
    }

    public long getVersion() {
        return version_tabla;
    }

    public String getNombre() {
        return nombre_tabla;
    }
    public String toString() {
        return "Nombre tabla: " + nombre_tabla + "\nVersion: " +version_tabla;

    }
}
