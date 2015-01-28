package es.aplicacion.guiacastulo.db.model;

import es.aplicacion.guiacastulo.Utilidades.Utils;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class Recorrido {
    private long id;
    private String nombre;
    private String descripcion;
    //TODO tipo de distancia y tiempo
    private String distancia;
    private String duracion;
    private long [] id_marcadores;
    private String [] uri_imagen;
    private String [] uri_video;
    private String [] uri_audio;


    // setters

    /**
     * Inicializa Id del recorrido.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Inicializa nombre del recorrido.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Inicializa descripcion del recorrido.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Inicializa descripcion del recorrido.
     */
    public void setDuracion(String tiempo) {
        this.duracion = tiempo;
    }

    /**
     * Inicializa descripcion del recorrido.
     */
    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    /**
     * Inicializa la direccion donde esta alojada la imagen del recorrido.
     */
    public void setUriImagen(String [] uri_imagen) {
        this.uri_imagen = uri_imagen;
    }

    /**
     * Inicializa la direccion donde esta alojado el video del punto de interes.
     */
    public void setUriVideo(String [] uri_video) {
        this.uri_video = uri_video;
    }

    /**
     * Inicializa la direccion donde esta alojado el audio del punto de interes.
     */
    public void setUriAudio(String [] uri_audio) {
        this.uri_audio = uri_audio;
    }

    /**
     * Inicializa Ids de los marcadores contenidos en este recorrido.
     */
    public void setIdsMarcadores(long [] id_marcadores) {
        this.id_marcadores = id_marcadores;
    }

    // getters

    /**
     * Obtiene la Id del recorrido.
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
     * Obtiene la duracion del recorrido
     * @return
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Obtiene la distancia del recorrido
     * @return
     */
    public String getDistancia() {
        return distancia;
    }
    /**
     * Obtiene la/s direccion donde esta guardada la imagen.
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
    public String [] getUriVideo() {
        return uri_video;
    }
    /**
     * Obtiene la/s direccion donde esta guardado el audio/s.
     *
     * @return String uri_imagen.
     */
    public String [] getUriAudio() {
        return uri_audio;
    }

    /**
     * Obtiene Ids de los marcadores contenidos en este recorrido.
     *
     * @return long [] id_marcadores.
     */
    public long [] getId_marcadores() {
        return id_marcadores;
    }

    /**
     * Recorrido -> To String
     * @return
     */
    public String toString() {
        return "\nId Recorrido: " + id + "\nNombre: " + nombre + "\nDescrición: " + descripcion+
                 "\nDuracion (horas): " + duracion+ "\nDistancia (km): " + distancia+
                "\nIds marcadores: " + Utils.crearStringComas(id_marcadores)
                +
                "\nUriImagen: "+ Utils.crearStringComas(uri_imagen)+
                "\nUriVideo: "+Utils.crearStringComas(uri_video)+
                "\nUriAudio: "+Utils.crearStringComas(uri_audio);
    }
}
