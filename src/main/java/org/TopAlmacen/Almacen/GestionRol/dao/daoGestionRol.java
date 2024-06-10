package org.TopAlmacen.Almacen.GestionRol.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;

import java.sql.SQLException;
import java.util.List;

public interface daoGestionRol extends Crud<Rol> {

    void cambioEstado(Rol rol) throws SQLException;

    List<Rol> listaRolActivo() throws SQLException;
}
