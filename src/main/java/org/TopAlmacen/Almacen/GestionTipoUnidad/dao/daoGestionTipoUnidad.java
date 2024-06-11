package org.TopAlmacen.Almacen.GestionTipoUnidad.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionTipoUnidad.model.TipoUnidad;

import java.sql.SQLException;
import java.util.List;

public interface daoGestionTipoUnidad extends Crud<TipoUnidad> {

    void cambiarestado(TipoUnidad tipoUnidad) throws SQLException;
    List<TipoUnidad> listarActivo() throws SQLException;

}
