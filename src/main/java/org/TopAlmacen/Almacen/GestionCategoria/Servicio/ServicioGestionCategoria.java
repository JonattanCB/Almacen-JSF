package org.TopAlmacen.Almacen.GestionCategoria.Servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionCategoria.dao.Impl.ImplGestionCategoria;
import org.TopAlmacen.Almacen.GestionCategoria.dao.daoGestionCategoria;
import org.TopAlmacen.Almacen.GestionCategoria.model.Categoria;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioGestionCategoria  implements Serializable {
    private final daoGestionCategoria dao;

    public ServicioGestionCategoria() {
        dao = new ImplGestionCategoria();
    }

    public Categoria buscarCategoria(int id) throws SQLException {
        return  dao.buscar(id);
    }

    public void actualizar(Categoria categoria) throws SQLException {
        dao.actualizar(categoria);
    }

    public List<Categoria> lstCategoria() throws SQLException {
        return  dao.buscarTodos();
    }

    public void registrarCategoria(Categoria c) throws SQLException {
        dao.registrar(c);
    }

    public  void CambiarEstado(Categoria c) throws  SQLException {
        dao.CambiarEstado(c);
    }

}
