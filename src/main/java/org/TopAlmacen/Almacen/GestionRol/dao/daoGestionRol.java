package org.TopAlmacen.Almacen.GestionRol.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;

import java.sql.SQLException;

public interface daoGestionRol extends Crud<Rol> {

    void cambioEstado(Rol rol) throws SQLException;

}
