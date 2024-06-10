package org.TopAlmacen.Almacen.GestionTipoEmpresa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoEmpresa {
    private int id;
    private String nombre;
    private String abreviatura;
    private String estado;
    private Timestamp Cfecha;
}
