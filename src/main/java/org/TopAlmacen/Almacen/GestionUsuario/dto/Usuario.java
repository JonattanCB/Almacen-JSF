package org.TopAlmacen.Almacen.GestionUsuario.dto;

import org.TopAlmacen.Almacen.GestionPersona.dto.Persona;
import org.TopAlmacen.Almacen.GestionRol.dto.Rol;

import java.sql.Timestamp;

public class Usuario {

    private int id;
    private String Correo;
    private String contraseña;
    private Persona personas;
    private Rol rol;
    private String estado;
    private Timestamp Cfecha;

    public Usuario() {
    }

    public Usuario(int id, String correo, String contraseña, Persona personas, Rol rol, String estado, Timestamp cfecha) {
        this.id = id;
        Correo = correo;
        this.contraseña = contraseña;
        this.personas = personas;
        this.rol = rol;
        this.estado = estado;
        Cfecha = cfecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Persona getPersonas() {
        return personas;
    }

    public void setPersonas(Persona personas) {
        this.personas = personas;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getCfecha() {
        return Cfecha;
    }

    public void setCfecha(Timestamp cfecha) {
        Cfecha = cfecha;
    }
}
