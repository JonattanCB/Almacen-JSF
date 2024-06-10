package org.TopAlmacen.Almacen.GestionStock.dao.impl;

import org.TopAlmacen.Almacen.GestionProducto.dao.daoGestionProducto;
import org.TopAlmacen.Almacen.GestionProducto.dao.impl.ImplGestionProducto;
import org.TopAlmacen.Almacen.GestionStock.dao.daoGestionStock;
import org.TopAlmacen.Almacen.GestionStock.model.Stock;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ImplGestionStock implements daoGestionStock {

    private final conexion con;
    private final daoGestionProducto daoproducto;

    public ImplGestionStock() {
        this.con = new conexion();
        daoproducto = new ImplGestionProducto();
    }

    @Override
    public void registrar(Stock stock) throws SQLException {
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("INSERT INTO public.\"Stock\"(\n" +
                    "\t producto, cantidad)\n" +
                    "\tVALUES ( ?, ?);");
            ps.setInt(1,stock.getProducto().getIdProducto());
            ps.setInt(2,stock.getCant());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Stock stock) throws SQLException {
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("UPDATE public.\"Stock\"\n" +
                    "\tSET cantidad=?\n" +
                    "\tWHERE id=?;");
            ps.setInt(1,stock.getCant());
            ps.setInt(2,stock.getId());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(Stock stock) throws SQLException {

    }

    @Override
    public Stock buscar(int t) throws SQLException {
        Stock stock = new Stock();
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("SELECT id, producto, cantidad\n" +
                    "\tFROM public.\"Stock\" where id = ?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                stock.setId(rs.getInt(1));
                stock.setProducto(daoproducto.buscar(rs.getInt(2)));
                stock.setCant(rs.getInt(3));
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return stock;
    }

    @Override
    public List<Stock> buscarTodos() throws SQLException {
        return List.of();
    }
}
