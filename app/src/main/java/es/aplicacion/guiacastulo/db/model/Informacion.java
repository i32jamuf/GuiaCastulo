package es.aplicacion.guiacastulo.db.model;

/**
 * /**
 * Informacion, se compone de: <code>id</code>, <code>horario</code>,
 * <code>telefono</code>, <code>direccion</code>, <code>web</code> y <code>mas_info</code>.
 * Created by Enmanuel on 21/01/2015.
 */
public class Informacion {
    private String horario;
    private String telefono;
    private String direccion;
    private String web;
    private String mas_info;
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
     * Inicializa horario del objeto informacion.
     */
    public void setHorario(String horario) {
        this.horario = horario;
    }

    /**
     * Inicializa telefono del objeto informacion.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Inicializa direccion del objeto informacion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Inicializa web del objeto informacion.
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     * Inicializa mas_info del objeto informacion.
     */
    public void setMasInfo(String mas_info) {
        this.mas_info = mas_info;
    }


    // getters

    /**
     * Obtiene el horario.
     *
     * @return String horario.
     */
    public String getHorario() {
        return horario;
    }

    /**
     * Obtiene el telefono.
     *
     * @return String telefono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Obtiene la direccion.
     *
     * @return String direccion.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Obtiene la web.
     *
     * @return String direccion.
     */
    public String getWeb() {
        return web;
    }

    /**
     * Obtiene mas informacion.
     *
     * @return String mas_info.
     */
    public String getMasInfo() {
        return mas_info;
    }


    public long getIdServidor() {
        return id_servidor;
    }

    public long getVersion() {
        return version;
    }

    /**
     * Informacion -> To String
     * @return
     */
    public String toString() {
        return "\nDirección: " + direccion
                + "\nHorario: " + horario + "\nTelf: " + telefono+ "\nWeb: "+web+
                "\nMás Info: " +mas_info+
                "\nId servidor: " + id_servidor+  "\nVersion: "+ version;

    }
}
