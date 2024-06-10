package org.TopAlmacen.Almacen.GestionTipoUnidad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoUnidad {
    private int id;
    private String nombre;
    private String abrev;
    private String estado;
    private Timestamp Cfecha;
}
