package es.aplicacion.guiacastulo.db.model;

import android.location.Location;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class Marcadores {
    private long id;
    private long [] id_recorridos;
    private long [] id_puntos_interes;
    private String nombre;
    private String descripcion;
    private String uri_imagen;
    private Location location;


    // setters

    /**
     * Inicializa Id del objeto marcador.
     */
    public void setId(long marcadorId) {
        this.id = marcadorId;
    }

    /**
     * Inicializa Ids de los recorridos que usan este marcador.
     */
    public void setIdsRecorridos(long [] id_recorridos) {

        this.id_recorridos = id_recorridos;
    }

    /**
     * Inicializa Ids de los puntos de interes contenidos en este marcador.
     */
    public void setIdsPoI(long [] id_puntos_interes) {
        this.id_puntos_interes = id_puntos_interes;
    }

    /**
     * Inicializa nombre del marcador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Inicializa descripcion del marcador.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Inicializa la direccion donde esta alojada la imagen del marcador.
     */
    public void setUriImagen(String uri_imagen) {
        this.uri_imagen = uri_imagen;
    }

    /**
     * Inicializa la localizacion {@link android.location.Location} del marcador.
     */
    public void setLocation(Location location) {
        this.location = location;
    }



    // getters

    /**
     * Obtiene la Id del objeto informacion.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Obtiene Ids de los recorridos que usan este marcador.
     *
     * @return long [] id_recorridos.
     */
    public long [] getId_recorridos() {
        return id_recorridos;
    }


    /**
     * Obtiene Ids de los puntos de interes contenidos en este marcador.
     *
     * @return long [] id_puntos_interes.
     */
    public long [] getId_puntos_interes() {
        return id_puntos_interes;
    }

    /**
     * Obtiene el nombre.
     *
     * @return String nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripcion.
     *
     * @return String descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la direccion donde esta guardada la imagen.
     *
     * @return String uri_imagen.
     */
    public String getUriImagen() {
        return uri_imagen;
    }

    /**
     * Obtiene la localizacion (coordenadas) del marcador.
     *
     * @return location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Marcador -> To String
     * @return
     */
    public String toString() {
        return "Id Marcador: " + id + "\n" + "\nIds Recorridos: " + id_recorridos.toString()
                + "\nIds PoI: " + id_puntos_interes.toString()
                + "\nNombre: " + nombre + "\nDescrición: " + descripcion+
                "\nUriImagen: "+uri_imagen+
                "\nLocalización: " +location.toString();

    }
}
