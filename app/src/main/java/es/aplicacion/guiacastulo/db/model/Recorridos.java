package es.aplicacion.guiacastulo.db.model;

/**
 * Created by Enmanuel on 21/01/2015.
 */
public class Recorridos {
    private long id;
    private String nombre;
    private String descripcion;
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
     * Recorrido -> To String
     * @return
     */
    public String toString() {
        return "Id PoI: " + id + "\nNombre: " + nombre + "\nDescrición: " + descripcion+
                "\nUriImagen: "+uri_imagen.toString()+
                "\nUriVideo: "+uri_video.toString()+
                "\nUriAudio: "+uri_audio.toString();
    }
}
