package org.TopAlmacen.Almacen.GestionProveedor.dao.Impl;

import org.TopAlmacen.Almacen.GestionProveedor.dao.daoGestionProveedor;
import org.TopAlmacen.Almacen.GestionProveedor.model.Proveedor;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.Impl.ImplGestionTipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.daoTipoEmpresa;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionProveedor implements daoGestionProveedor {

    private final conexion con;
    private final daoTipoEmpresa daoTE;

    public ImplGestionProveedor() {
        con = new conexion();
        daoTE = new ImplGestionTipoEmpresa();
    }

    @Override
    public void registrar(Proveedor proveedor) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.proveedor(\n" +
                    "\t\"NroRUC\", nombre, tipoempesa, direccion, telefono, correo, estado, \"Cfecha\")\n" +
                    "\tVALUES ( ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setInt(1, proveedor.getNroRUC());
            ps.setString(2, proveedor.getNombre());
            ps.setInt(3, proveedor.getTipoEmpresa().getId());
            ps.setString(4, proveedor.getDireccion());
            ps.setInt(5,proveedor.getTelefono());
            ps.setString(6,proveedor.getCorreo());
            ps.setString(7,proveedor.getEstado());
            ps.setTimestamp(8,proveedor.getCfecha());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Proveedor proveedor) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.proveedor\n" +
                    "\tSET \"NroRUC\"=?, nombre=?, tipoempesa=?, direccion=?, telefono=?, correo=?\n" +
                    "\tWHERE id=?;");

            ps.setInt(1, proveedor.getNroRUC());
            ps.setString(2, proveedor.getNombre());
            ps.setInt(3, proveedor.getTipoEmpresa().getId());
            ps.setString(4, proveedor.getDireccion());
            ps.setInt(5,proveedor.getTelefono());
            ps.setString(6,proveedor.getCorreo());
            ps.setInt(7,proveedor.getIdProveedor());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(Proveedor proveedor) throws SQLException {

    }

    @Override
    public Proveedor buscar(int t) throws SQLException {
        Proveedor p = new Proveedor();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, \"NroRUC\", nombre, tipoempesa, direccion, telefono, correo, estado, \"Cfecha\"\n" +
                    "\tFROM public.proveedor where id = ? ;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                p.setIdProveedor(rs.getInt(1));
                p.setNroRUC(rs.getInt(2));
                p.setNombre(rs.getString(3));
                p.setTipoEmpresa(daoTE.buscar(rs.getInt(4)));
                p.setDireccion(rs.getString(5));
                p.setTelefono(rs.getInt(6));
                p.setCorreo(rs.getString(7));
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
    public List<Proveedor> buscarTodos() throws SQLException {
        List<Proveedor> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, \"NroRUC\", nombre, tipoempesa, direccion, telefono, correo, estado, \"Cfecha\"\n" +
                    "\tFROM public.proveedor order by id asc;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Proveedor p = new Proveedor();
                p.setIdProveedor(rs.getInt(1));
                p.setNroRUC(rs.getInt(2));
                p.setNombre(rs.getString(3));
                p.setTipoEmpresa(daoTE.buscar(rs.getInt(4)));
                p.setDireccion(rs.getString(5));
                p.setTelefono(rs.getInt(6));
                p.setCorreo(rs.getString(7));
                p.setEstado(rs.getString(8));
                p.setCfecha(rs.getTimestamp(9));
                lst.add(p);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return lst;
    }

    @Override
    public void CambioEstado(Proveedor proveedor) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.proveedor\n" +
                    "\tSET estado=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, proveedor.getEstado());
            ps.setInt(2,proveedor.getIdProveedor());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public List<Proveedor> lstProveedorActivo() throws SQLException {
        List<Proveedor> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, \"NroRUC\", nombre, tipoempesa, direccion, telefono, correo, estado, \"Cfecha\"\n" +
                    "\tFROM public.proveedor where estado='Activo';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Proveedor p = new Proveedor();
                p.setIdProveedor(rs.getInt(1));
                p.setNroRUC(rs.getInt(2));
                p.setNombre(rs.getString(3));
                p.setTipoEmpresa(daoTE.buscar(rs.getInt(4)));
                p.setDireccion(rs.getString(5));
                p.setTelefono(rs.getInt(6));
                p.setCorreo(rs.getString(7));
                p.setEstado(rs.getString(8));
                p.setCfecha(rs.getTimestamp(9));
                lst.add(p);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return lst;
    }
}
