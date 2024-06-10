package org.TopAlmacen.Almacen.GestionStock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.TopAlmacen.Almacen.GestionProducto.model.Producto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private int id;
    private Producto producto;
    private int cant;
}
