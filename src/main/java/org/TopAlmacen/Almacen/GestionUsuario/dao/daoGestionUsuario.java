package org.TopAlmacen.Almacen.GestionUsuario.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionUsuario.model.Usuario;

import java.sql.SQLException;

public interface daoGestionUsuario extends Crud<Usuario> {

    boolean verificadorUsuario(Usuario usuario) throws SQLException;

    void  cambiarEstado(Usuario usuario) throws  SQLException;

    boolean verificarUsuarioRepetido(Usuario usuario) throws SQLException;

    Usuario traerLoginUsuario(Usuario usuario) throws  SQLException;

}
