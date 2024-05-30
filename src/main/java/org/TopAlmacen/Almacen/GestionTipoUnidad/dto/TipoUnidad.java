package org.TopAlmacen.Almacen.GestionTipoUnidad.dto;

public class TipoUnidad {
    private int id;
    private String nombre;
    private String abrev;

    public TipoUnidad() {
    }

    public TipoUnidad(int id, String nombre, String abrev) {
        this.id = id;
        this.nombre = nombre;
        this.abrev = abrev;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
