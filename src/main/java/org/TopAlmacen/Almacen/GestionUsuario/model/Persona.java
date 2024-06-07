package org.TopAlmacen.Almacen.GestionUsuario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    private int id;
    private String pnombre;
    private String snombre;
    private String apatero;
    private String amatero;
    private TipoDocumento tipoDocumento;
    private int ndocumento;
    private String direccion;
    private int telefono;
    private String estado;
}
