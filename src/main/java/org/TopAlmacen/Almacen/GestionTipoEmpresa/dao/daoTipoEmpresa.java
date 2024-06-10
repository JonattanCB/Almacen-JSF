package org.TopAlmacen.Almacen.GestionTipoEmpresa.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.model.TipoEmpresa;

import java.sql.SQLException;
import java.util.List;

public interface daoTipoEmpresa extends Crud<TipoEmpresa> {
    void CambioEstado(TipoEmpresa tipoEmpresa) throws SQLException;
    List<TipoEmpresa> listTipoEmpresaActivo() throws  SQLException;
}
