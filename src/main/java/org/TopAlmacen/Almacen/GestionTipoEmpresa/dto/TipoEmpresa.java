package org.TopAlmacen.Almacen.GestionTipoEmpresa.dto;

public class TipoEmpresa {
    private int id;
    private String nombre;
    private String abreviatura;

    public TipoEmpresa() {
    }

    public TipoEmpresa(int id, String nombre, String abreviatura) {
        this.id = id;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
