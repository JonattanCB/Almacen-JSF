package org.TopAlmacen.Almacen.GestionProducto.dao.impl;

import org.TopAlmacen.Almacen.GestionCategoria.dao.Impl.ImplGestionCategoria;
import org.TopAlmacen.Almacen.GestionCategoria.dao.daoGestionCategoria;
import org.TopAlmacen.Almacen.GestionProducto.dao.daoGestionProducto;
import org.TopAlmacen.Almacen.GestionProducto.model.Producto;
import org.TopAlmacen.Almacen.GestionProveedor.dao.Impl.ImplGestionProveedor;
import org.TopAlmacen.Almacen.GestionProveedor.dao.daoGestionProveedor;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dao.Impl.ImplGestionTipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dao.daoGestionTipoUnidad;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionProducto implements daoGestionProducto {

    private final conexion con;
    private final daoGestionTipoUnidad daotu;
    private final daoGestionCategoria daocate;
    private final daoGestionProveedor daopro;

    public ImplGestionProducto() {
        this.con = new conexion();
        daotu = new ImplGestionTipoUnidad();
        daocate = new ImplGestionCategoria();
        daopro = new ImplGestionProveedor();
    }

    @Override
    public void registrar(Producto producto) throws SQLException {
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("INSERT INTO public.productos(\n" +
                    "\t nombre, descripcion, foto, categoria, tipounidad, proveedor, estado, \"Cfecha\")\n" +
                    "\tVALUES ( ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1,producto.getNombre());
            ps.setString(2,producto.getDescripcion());
            ps.setBytes(3,producto.getFoto());
            ps.setInt(4,producto.getCategoria().getId());
            ps.setInt(5,producto.getTipoUnidad().getId());
            ps.setInt(6,producto.getProveedor().getIdProveedor());
            ps.setString(7 , producto.getEstado());
            ps.setTimestamp(8, producto.getCfecha());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Producto producto) throws SQLException {
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("UPDATE public.productos\n" +
                    "\tSET nombre=?, descripcion=?, foto=?, categoria=?, tipounidad=?, proveedor=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setBytes(3, producto.getFoto());
            ps.setInt(4, producto.getCategoria().getId());
            ps.setInt(5, producto.getTipoUnidad().getId());
            ps.setInt(6, producto.getProveedor().getIdProveedor());
            ps.setInt(7, producto.getIdProducto());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(Producto producto) throws SQLException {

    }

    @Override
    public Producto buscar(int t) throws SQLException {
        Producto p = new Producto();
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("SELECT id, nombre, descripcion, foto, categoria, tipounidad, proveedor, estado, \"Cfecha\"\n" +
                    "\tFROM public.productos where id=?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                p.setIdProducto(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setDescripcion(rs.getString(3));
                p.setFoto(rs.getBytes(4));
                p.setCategoria(daocate.buscar(rs.getInt(5)));
                p.setTipoUnidad(daotu.buscar(rs.getInt(6)));
                p.setProveedor(daopro.buscar(rs.getInt(7)));
                p.setEstado(rs.getString(8));
                p.setCfecha(rs.getTimestamp(9));
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return p;
    }

    @Override
    public List<Producto> buscarTodos() throws SQLException {
        List<Producto> list = new ArrayList<>();
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("SELECT id, nombre, descripcion, foto, categoria, tipounidad, proveedor, estado, \"Cfecha\"\n" +
                    "\tFROM public.productos;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Producto p = new Producto();
                p.setIdProducto(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setDescripcion(rs.getString(3));
                p.setFoto(rs.getBytes(4));
                p.setCategoria(daocate.buscar(rs.getInt(5)));
                p.setTipoUnidad(daotu.buscar(rs.getInt(6)));
                p.setProveedor(daopro.buscar(rs.getInt(7)));
                p.setEstado(rs.getString(8));
                p.setCfecha(rs.getTimestamp(9));
                list.add(p);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return list;
    }

    @Override
    public int registraProductoRetornaID(Producto producto) throws SQLException {
        int nuevoid=0;
        try {
            PreparedStatement ps =con.crearCNX().prepareStatement("INSERT INTO public.productos(\n" +
                    "\t nombre, descripcion, foto, categoria, tipounidad, proveedor, estado, \"Cfecha\")\n" +
                    "\tVALUES ( ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id ;");
            ps.setString(1,producto.getNombre());
            ps.setString(2,producto.getDescripcion());
            ps.setBytes(3,producto.getFoto());
            ps.setInt(4,producto.getCategoria().getId());
            ps.setInt(5,producto.getTipoUnidad().getId());
            ps.setInt(6,producto.getProveedor().getIdProveedor());
            ps.setString(7 , producto.getEstado());
            ps.setTimestamp(8, producto.getCfecha());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                nuevoid = rs.getInt(1);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return nuevoid;
    }
}
