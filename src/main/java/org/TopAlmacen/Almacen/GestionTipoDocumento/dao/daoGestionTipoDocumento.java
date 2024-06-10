package org.TopAlmacen.Almacen.GestionTipoDocumento.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;

import java.sql.SQLException;

public interface daoGestionTipoDocumento  extends Crud<TipoDocumento> {
    void CambiarEstado(TipoDocumento tipoDocumento) throws SQLException;
}
