package org.TopAlmacen.Almacen.GestionStock.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionStock.dao.daoGestionStock;
import org.TopAlmacen.Almacen.GestionStock.dao.impl.ImplGestionStock;
import org.TopAlmacen.Almacen.GestionStock.model.Stock;

import java.io.Serializable;
import java.sql.SQLException;

@Stateless
@LocalBean
public class ServicioGestionStock implements Serializable {
    private final daoGestionStock dao;

    public ServicioGestionStock() {
        dao = new ImplGestionStock();
    }

    public void RegistrarStock(Stock stock) throws SQLException {
        dao.registrar(stock);
    }

    public void ActualizarStock(Stock stock) throws SQLException {
        dao.actualizar(stock);
    }

    public  Stock traerStock(int id) throws SQLException {
        return dao.buscar(id);
    }

}
