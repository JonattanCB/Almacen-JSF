package org.TopAlmacen.Almacen.GestionProducto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.TopAlmacen.Almacen.GestionCategoria.model.Categoria;
import org.TopAlmacen.Almacen.GestionTipoUnidad.model.TipoUnidad;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private byte[] foto;
    private Categoria categoria;
    private TipoUnidad tipoUnidad;

}
