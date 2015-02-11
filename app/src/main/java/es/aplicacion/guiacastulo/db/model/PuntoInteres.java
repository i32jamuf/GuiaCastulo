package es.aplicacion.guiacastulo.db.model;

import android.location.Location;

import es.aplicacion.guiacastulo.Utilidades.Utils;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class PuntoInteres {
    private long id;
    private String nombre;
    private String descripcion;
    private String [] uri_imagen;
    private String uri_video;
    private String uri_audio;
    private double latitud;
    private double longitud;
    private long id_servidor;
    private long version;


    // setters

    public void setIdServidor(long id_remota) {
        this.id_servidor = id_remota;
    }
    public void setVersion(long version) {
        this.version = version;
    }
    /**
     * Inicializa Id del punto de interes.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Inicializa nombre del punto de interés.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Inicializa descripcion del punto de interés.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Inicializa la direccion donde esta alojada la imagen del punto de interes.
     */
    public void setUriImagen(String [] uri_imagen) {
        this.uri_imagen = uri_imagen;
    }

    /**
     * Inicializa la direccion donde esta alojado el video del punto de interes.
     */
    public void setUriVideo(String uri_video) {
        this.uri_video = uri_video;
    }

    /**
     * Inicializa la direccion donde esta alojado el audio del punto de interes.
     */
    public void setUriAudio(String uri_audio) {
        this.uri_audio = uri_audio;
    }

    /**
     * Inicializa la latitud del punto de interes.
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Inicializa la longitud del punto de interes.
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }


    // getters

    /**
     * Obtiene la Id del punto de interes.
     *
     * @return id
     */
    public long getId() {
        return id;
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
     * Obtiene la/s direccion donde esta guardada la imagen/es.
     *
     * @return String uri_imagen.
     */
    public String [] getUriImagen() {
        return uri_imagen;
    }

    /**
     * Obtiene la/s direccion donde esta guardado el video/s.
     *
     * @return String uri_imagen.
     */
    public String getUriVideo() {
        return uri_video;
    }
    /**
     * Obtiene la/s direccion donde esta guardado el audio/s.
     *
     * @return String uri_imagen.
     */
    public String getUriAudio() {
        return uri_audio;
    }
    /**
     * Obtiene la latitud del punto de interes.
     *
     * @return location.
     */
    public double getLatitud() {
        return latitud;
    }
    /**
     * Obtiene la latitud del punto de interes.
     *
     * @return location.
     */
    public double getLongitud() {
        return longitud;
    }
    public long getIdServidor() {
        return id_servidor;
    }

    public long getVersion() {
        return version;
    }

    /**
     * PoI -> To String
     * @return
     */
    public String toString() {
        return "\nId PoI: " + id + "\nNombre: " + nombre + "\nDescrición: " + descripcion+
                "\nUriImagen: "+Utils.crearStringComas(uri_imagen)+
                "\nUriVideo: "+(uri_video)+
                "\nUriAudio: "+ (uri_audio)+
                "\nCoordenadas: " +latitud+" , "+longitud+
                "\nId servidor: " + id_servidor+  "\nVersion: "+ version;
    }
}
