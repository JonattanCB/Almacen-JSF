package org.TopAlmacen.Almacen.GestionPersona.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionPersona.model.Persona;

import java.sql.SQLException;

public interface daoGestionPersona extends Crud<Persona> {
    int registraPersonaRetornaID(Persona persona) throws SQLException;
}
