package org.TopAlmacen.Almacen.GestionUsuario.dao.Impl;

import org.TopAlmacen.Almacen.GestionUsuario.dao.daoGestionUsuario;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.SQLException;
import java.util.List;

public class ImplGestionUsuario  implements daoGestionUsuario {

    private conexion conn;

    @Override
    public void registrar(Object o) throws SQLException {

    }

    @Override
    public void actualizar(Object o) throws SQLException {

    }

    @Override
    public void eliminar(Object o) throws SQLException {

    }

    @Override
    public Object buscar(int t) throws SQLException {
        return null;
    }

    @Override
    public List buscarTodos() throws SQLException {
        return List.of();
    }
}
