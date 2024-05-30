package org.TopAlmacen.Almacen.GestionPersona.dto;

import org.TopAlmacen.Almacen.GestionTipoDocumento.dto.TipoDocumento;

public class Persona {

    private int id;
    private String pnombre;
    private String snombre;
    private String apatero;
    private String amatero;
    private TipoDocumento tipoDocumento;
    private int ndocumento;
    private String direccion;
    private int telefono;
    private String estado;

    public Persona() {
    }

    public Persona(int id, String pnombre, String snombre, String apatero, String amatero, TipoDocumento tipoDocumento, int ndocumento, String direccion, int telefono, String estado) {
        this.id = id;
        this.pnombre = pnombre;
        this.snombre = snombre;
        this.apatero = apatero;
        this.amatero = amatero;
        this.tipoDocumento = tipoDocumento;
        this.ndocumento = ndocumento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPnombre() {
        return pnombre;
    }

    public void setPnombre(String pnombre) {
        this.pnombre = pnombre;
    }

    public String getSnombre() {
        return snombre;
    }

    public void setSnombre(String snombre) {
        this.snombre = snombre;
    }

    public String getApatero() {
        return apatero;
    }

    public void setApatero(String apatero) {
        this.apatero = apatero;
    }

    public String getAmatero() {
        return amatero;
    }

    public void setAmatero(String amatero) {
        this.amatero = amatero;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getNdocumento() {
        return ndocumento;
    }

    public void setNdocumento(int ndocumento) {
        this.ndocumento = ndocumento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
