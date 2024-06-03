package org.TopAlmacen.Almacen.GestionTipoDocumento.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.Impl.ImplGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.daoGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioTipoDocumento implements Serializable {

    private final daoGestionTipoDocumento dao =new ImplGestionTipoDocumento();

    public List<TipoDocumento> listaTipoDocumento() throws SQLException {
        List<TipoDocumento> lst = dao.buscarTodos();
        return  lst;
    }

    public void guardarTipoDocumento(TipoDocumento td) throws  SQLException {
        dao.registrar(td);
    }

    public TipoDocumento buscarTipoDocumento(int id) throws SQLException {
        return dao.buscar(id);
    }

    public void ActualizarTipoDocumento(TipoDocumento td) throws  SQLException{
        dao.actualizar(td);
    }
}
