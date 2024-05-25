package org.TopAlmacen.Almacen.GestionJefesArea.dto;

public class JefesArea {

    private int id;
    private String nombre;
    private String area;
    private int numero;

    public JefesArea() {}

    public JefesArea(int id, String nombre, String area, int numero) {
        this.id = id;
        this.nombre = nombre;
        this.area = area;
        this.numero = numero;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "JefesArea{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", area='" + area + '\'' +
                ", numero=" + numero +
                '}';
    }
}
