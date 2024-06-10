package org.TopAlmacen.Almacen.GestionTipoDocumento.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;

import java.sql.SQLException;
import java.util.List;

public interface daoGestionTipoDocumento  extends Crud<TipoDocumento> {
    void CambiarEstado(TipoDocumento tipoDocumento) throws SQLException;
    List<TipoDocumento> listarTDocumentoActivo() throws  SQLException;
}
