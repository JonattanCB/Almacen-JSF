package org.TopAlmacen.Almacen.GestionProducto.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionProducto.model.Producto;

import java.sql.SQLException;

public interface daoGestionProducto extends Crud<Producto> {
    int registraProductoRetornaID(Producto producto) throws SQLException;
}
