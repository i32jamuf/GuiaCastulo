package es.aplicacion.guiacastulo.db.model;

/**
 * /**
 * Informacion, se compone de: <code>id</code>, <code>horario</code>,
 * <code>telefono</code>, <code>direccion</code>, <code>web</code> y <code>mas_info</code>.
 * Created by Enmanuel on 21/01/2015.
 */
public class Informacion {
    private long id;
    private String horario;
    private String telefono;
    private String direccion;
    private String web;
    private String mas_info;


    // setters

    /**
     * Inicializa Id del objeto informacion.
     */
    public void setId(long infoId) {
        this.id = infoId;
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
     * Obtiene la Id del objeto informacion.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

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

    /**
     * Informacion -> To String
     * @return
     */
    public String toString() {
        return "Id Informacion: " + id + "\n" + "\nDirección: " + direccion
                + "\nHorario: " + horario + "\nTelf: " + telefono+ "\nWeb: "+web+
                "\nMás Info: " +mas_info;

    }
}
