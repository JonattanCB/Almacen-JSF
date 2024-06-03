package org.TopAlmacen.Almacen.GestionTipoEmpresa.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.Impl.ImplGestionTipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.daoTipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.model.TipoEmpresa;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioGestionTipoEmpresa implements Serializable {

    private final daoTipoEmpresa dao = new ImplGestionTipoEmpresa();
    public List<TipoEmpresa> lstTodo() throws SQLException {
        return  dao.buscarTodos();
    }

    public void registrar(TipoEmpresa te) throws SQLException {
        dao.registrar(te);
    }

    public void modificar(TipoEmpresa te) throws SQLException {
        dao.actualizar(te);
    }

    public TipoEmpresa buscar(int id) throws SQLException {
        return dao.buscar(id);
    }


}
