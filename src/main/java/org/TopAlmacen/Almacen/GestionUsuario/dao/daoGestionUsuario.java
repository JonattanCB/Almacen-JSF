package org.TopAlmacen.Almacen.GestionUsuario.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionUsuario.model.Usuario;

import java.sql.SQLException;

public interface daoGestionUsuario extends Crud<Usuario> {

    boolean verificadorUsuario(Usuario usuario) throws SQLException;
}
