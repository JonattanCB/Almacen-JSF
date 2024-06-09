package org.TopAlmacen.Almacen.GestionProveedor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.model.TipoEmpresa;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    private int idProveedor;
    private int NroRUC;
    private String nombre;
    private TipoEmpresa tipoEmpresa;
    private String direccion;
    private int telefono;
    private String correo;
    private String estado;
    private Timestamp Cfecha;
}
