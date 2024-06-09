package org.TopAlmacen.Almacen.GestionProveedor.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionProveedor.dao.Impl.ImplGestionProveedor;
import org.TopAlmacen.Almacen.GestionProveedor.dao.daoGestionProveedor;
import org.TopAlmacen.Almacen.GestionProveedor.model.Proveedor;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioGestionProveedor implements Serializable {
    private final daoGestionProveedor dao;

    public ServicioGestionProveedor() {
       dao = new ImplGestionProveedor();
    }

    public List<Proveedor> proveedorList() throws SQLException {
        return dao.buscarTodos();
    }

    public Proveedor proveedor(int id) throws SQLException {
        return dao.buscar(id);
    }

    public void registrar(Proveedor proveedor) throws SQLException {
        dao.registrar(proveedor);
    }

    public void actualizar(Proveedor proveedor) throws SQLException {
        dao.actualizar(proveedor);
    }

}
