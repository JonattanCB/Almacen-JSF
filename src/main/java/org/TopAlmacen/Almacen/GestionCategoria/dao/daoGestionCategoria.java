package org.TopAlmacen.Almacen.GestionCategoria.dao;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionCategoria.model.Categoria;

@Stateless
@LocalBean
public interface daoGestionCategoria  extends Crud<Categoria> {
}
