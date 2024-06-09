package org.TopAlmacen.Almacen.GestionProducto.dao.impl;

import org.TopAlmacen.Almacen.GestionProducto.dao.daoGestionProducto;
import org.TopAlmacen.Almacen.GestionProducto.model.Producto;

import java.sql.SQLException;
import java.util.List;

public class ImplGestionProducto implements daoGestionProducto {

    @Override
    public void registrar(Producto producto) throws SQLException {

    }

    @Override
    public void actualizar(Producto producto) throws SQLException {

    }

    @Override
    public void eliminar(Producto producto) throws SQLException {

    }

    @Override
    public Producto buscar(int t) throws SQLException {
        return null;
    }

    @Override
    public List<Producto> buscarTodos() throws SQLException {
        return List.of();
    }
}
