package org.TopAlmacen.Almacen.GestionUsuario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String Correo;
    private String contrasenia;
    private Persona personas;
    private Rol rol;
    private String estado;
    private Timestamp Cfecha;
    private byte[] urlfoto;
}
