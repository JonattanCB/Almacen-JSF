package org.TopAlmacen.Almacen.GestionProducto.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionProducto.model.Producto;

import java.sql.SQLException;
import java.util.List;

public interface daoGestionProducto extends Crud<Producto> {
    int registraProductoRetornaID(Producto producto) throws SQLException;
    void CambioEstado(Producto producto) throws  SQLException;
    List<Producto> listProductoActivo() throws SQLException;
}
