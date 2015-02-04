package es.aplicacion.guiacastulo.db.model;

import android.location.Location;

/**
 * Created by Enmanuel on 04/02/2015.
 */
public class Coordenada {

    private long id;
    private long id_recorrido;
    private double latitud;
    private double longitud;



    // setters
    /**
     * Inicializa Id del objeto coordenada.
     */
    public void setId(long coordId) {
        this.id = coordId;
    }

    /**
     * Inicializa Id del recorrido que usa la coordenada
     */
    public void setIdRecorrido(long IdRecorrido) {
        this.id_recorrido = IdRecorrido;
    }

    /**
     * Inicializa latitud.
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
    /**
     * Inicializa longitud.
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    // getters

    /**
     * Obtiene la Id del objeto coordenada.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Obtiene Id del objeto Recorrido
     *
     * @return Id del recorrido la coordenada
     */
    public long getIdRecorrido() {
        return id_recorrido;
    }


    /**
     * Inicializa latitud.
     */
    public double getLatitud() {
        return latitud;
    }
    /**
     * Inicializa longitud.
     */
    public double getLongitud() {
        return longitud;
    }
    public String toString() {
        return "Id Coordenada: " + id + "\n" + "IdRecorrido: " + id_recorrido
                +  "\nLatitud: "
                + latitud+  "\nLongitud: "
                + longitud;

    }

}
