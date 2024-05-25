package org.TopAlmacen.Almacen.GestionTipoDocumento.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.Impl.ImplGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.daoGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dto.TipoDocumento;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioTipoDocumento implements Serializable {
    private daoGestionTipoDocumento dao=new ImplGestionTipoDocumento();

    public List<TipoDocumento> listaTipoDocumento() throws SQLException {
        return  dao.buscarTodos();
    }
}
