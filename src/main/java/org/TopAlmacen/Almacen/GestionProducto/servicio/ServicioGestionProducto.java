package org.TopAlmacen.Almacen.GestionProducto.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionProducto.dao.daoGestionProducto;
import org.TopAlmacen.Almacen.GestionProducto.dao.impl.ImplGestionProducto;
import org.TopAlmacen.Almacen.GestionProducto.model.Producto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioGestionProducto implements Serializable {

    private  final daoGestionProducto dao ;

    public ServicioGestionProducto() {
        dao = new ImplGestionProducto();
    }

    public void registrarProducto(Producto producto) throws SQLException {
        dao.registrar(producto);
    }

    public void ActualizarProducto(Producto producto) throws SQLException {
        dao.actualizar(producto);
    }

    public Producto traerProducto(int id) throws SQLException {
        return dao.buscar(id);
    }

    public List<Producto> listProductos() throws SQLException {
        return dao.buscarTodos();
    }

    public int registraProductoRetornaID(Producto producto) throws SQLException {
        return  dao.registraProductoRetornaID(producto);
    }

}
