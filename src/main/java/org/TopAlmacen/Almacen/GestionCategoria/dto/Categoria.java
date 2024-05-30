package org.TopAlmacen.Almacen.GestionCategoria.dto;

import java.sql.Timestamp;

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Timestamp CFecha;

    public Categoria() {
    }

    public Categoria(int id, String nombre, String descripcion, String estado, Timestamp CFecha) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.CFecha = CFecha;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getCFecha() {
        return CFecha;
    }

    public void setCFecha(Timestamp CFecha) {
        this.CFecha = CFecha;
    }
}
