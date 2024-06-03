package org.TopAlmacen.Almacen.GestionCategoria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Timestamp CFecha;
}
