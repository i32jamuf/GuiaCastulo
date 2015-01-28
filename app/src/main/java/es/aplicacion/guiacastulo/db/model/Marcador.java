package es.aplicacion.guiacastulo.db.model;

import android.location.Location;

import es.aplicacion.guiacastulo.Utilidades.Utils;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class Marcador {
    private long id;
    private long [] ids_puntos_interes;
    private String nombre;
    private String descripcion;
    private String [] uri_imagen;
    private double latitud;
    private double longitud;


    // setters

    /**
     * Inicializa Id del objeto marcador.
     */
    public void setId(long marcadorId) {
        this.id = marcadorId;
    }


    /**
     * Inicializa Ids de los puntos de interes contenidos en este marcador.
     */
    public void setIdsPoI(long [] id_puntos_interes) {
        this.ids_puntos_interes = id_puntos_interes;
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
    public void setUriImagen(String [] uri_imagen) {
        this.uri_imagen = uri_imagen;
    }
    /**
     * Inicializa la latitud del marcador.
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Inicializa la longitud del marcador.
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
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
     * Obtiene Ids de los puntos de interes contenidos en este marcador.
     *
     * @return long [] id_puntos_interes.
     */
    public long [] getId_puntos_interes() {
        return ids_puntos_interes;
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
    public String [] getUriImagen() {
        return uri_imagen;
    }

    /**
     * Obtiene la latitud del marcador.
     *
     * @return location.
     */
    public double getLatitud() {
        return latitud;
    }
    /**
     * Obtiene la latitud del marcador.
     *
     * @return location.
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Marcador -> To String
     * @return
     */
    public String toString() {
        return "\nId Marcador: " + id + "\n" + "\nIds PoI: " + Utils.crearStringComas(ids_puntos_interes)
                + "\nNombre: " + nombre + "\nDescrición: " + descripcion+
                "\nUriImagen: "+Utils.crearStringComas(uri_imagen)+
                "\nCoordenadas: " +latitud+" , "+longitud;

    }
}
