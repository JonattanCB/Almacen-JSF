package org.TopAlmacen.Almacen.GestionUsuario.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionUsuario.model.Persona;

import java.sql.SQLException;

public interface daoGestionPersona extends Crud<Persona> {
    int registraPersonaRetornaID(Persona persona) throws SQLException;
}
