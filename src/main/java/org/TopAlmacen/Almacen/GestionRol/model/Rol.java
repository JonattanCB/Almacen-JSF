package org.TopAlmacen.Almacen.GestionRol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Timestamp Cfecha;
}
