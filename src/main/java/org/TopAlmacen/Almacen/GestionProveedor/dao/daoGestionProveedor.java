package org.TopAlmacen.Almacen.GestionProveedor.dao;

import org.TopAlmacen.Almacen.Dao.Crud;
import org.TopAlmacen.Almacen.GestionProveedor.model.Proveedor;

import java.sql.SQLException;
import java.util.List;

public interface daoGestionProveedor extends Crud<Proveedor> {
    void CambioEstado(Proveedor proveedor) throws SQLException;
    List<Proveedor> lstProveedorActivo() throws  SQLException;
}
