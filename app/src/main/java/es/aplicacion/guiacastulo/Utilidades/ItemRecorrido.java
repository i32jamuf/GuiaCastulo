package es.aplicacion.guiacastulo.Utilidades;

/**
 * Created by PUESTO02_EJCI on 22/01/2015.
 */
public class ItemRecorrido {



    protected long id;
    protected String rutaImagen;
    protected String titulo;
    protected String subtitulo;

    public ItemRecorrido() {
        this.titulo = "";
        this.subtitulo = "";
        this.rutaImagen = "";
    }

    public ItemRecorrido(long id, String titulo, String subtitulo) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.rutaImagen = "";
    }

    public ItemRecorrido(long id, String titulo, String subtitulo, String rutaImagen) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.rutaImagen = rutaImagen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //TODO falla aunque uses el constructor que no necesita rutaImagen
    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}



