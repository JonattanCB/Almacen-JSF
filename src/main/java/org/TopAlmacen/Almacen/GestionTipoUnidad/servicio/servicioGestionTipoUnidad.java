package org.TopAlmacen.Almacen.GestionTipoUnidad.servicio;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.SessionScoped;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dao.Impl.ImplGestionTipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dao.daoGestionTipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dto.TipoUnidad;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@SessionScoped
public class servicioGestionTipoUnidad implements Serializable {
    private final daoGestionTipoUnidad dao = new ImplGestionTipoUnidad();

    public List<TipoUnidad> lstTipoUnidad() throws SQLException {
        return  dao.buscarTodos();
    }

    public void registrar(TipoUnidad tu) throws SQLException {
        dao.registrar(tu);
    }

    public void modificar(TipoUnidad unidad) throws SQLException {
        dao.actualizar(unidad);
    }

    public TipoUnidad buscar(int id) throws SQLException {
        return dao.buscar(id);
    }

}
