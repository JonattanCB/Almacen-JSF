package org.TopAlmacen.Almacen.GestionCategoria.dao;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionCategoria.model.Categoria;

import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public interface daoGestionCategoria  extends Crud<Categoria> {

    void CambiarEstado(Categoria categoria) throws SQLException;
    List<Categoria> listarActivo() throws SQLException;
}
