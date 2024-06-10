package org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.Impl;

import org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.daoTipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.model.TipoEmpresa;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionTipoEmpresa implements daoTipoEmpresa {
    private final conexion con;

    public ImplGestionTipoEmpresa() {
        con = new conexion();
    }

    @Override
    public void registrar(TipoEmpresa tipoEmpresa) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.\"TipoEmpresa\"(\n" +
                    "\t nombre, abreviatura, estado, \"Cfecha\")\n" +
                    "\tVALUES ( ?, ?, ?, ?);");
            ps.setString(1, tipoEmpresa.getNombre());
            ps.setString(2, tipoEmpresa.getAbreviatura());
            ps.setString(3,tipoEmpresa.getEstado());
            ps.setTimestamp(4,tipoEmpresa.getCfecha());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(TipoEmpresa tipoEmpresa) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.\"TipoEmpresa\"\n" +
                    "\tSET nombre=?, abreviatura=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, tipoEmpresa.getNombre());
            ps.setString(2, tipoEmpresa.getAbreviatura());
            ps.setInt(3, tipoEmpresa.getId());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(TipoEmpresa tipoEmpresa) throws SQLException {

    }

    @Override
    public TipoEmpresa buscar(int t) throws SQLException {
        TipoEmpresa te = new TipoEmpresa();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abreviatura, estado, \"Cfecha\"\n" +
                    "\tFROM public.\"TipoEmpresa\" where  id = ?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                te.setId(rs.getInt(1));
                te.setNombre(rs.getString(2));
                te.setAbreviatura(rs.getString(3));
                te.setEstado(rs.getString(4));
                te.setCfecha(rs.getTimestamp(5));
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return  te;
    }

    @Override
    public List<TipoEmpresa> buscarTodos() throws SQLException {
        List<TipoEmpresa> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abreviatura, estado, \"Cfecha\"\n" +
                    "\tFROM public.\"TipoEmpresa\" order by id asc;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TipoEmpresa te = new TipoEmpresa();
                te.setId(rs.getInt(1));
                te.setNombre(rs.getString(2));
                te.setAbreviatura(rs.getString(3));
                te.setEstado(rs.getString(4));
                te.setCfecha(rs.getTimestamp(5));
                lst.add(te);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return  lst;
    }

    @Override
    public void CambioEstado(TipoEmpresa tipoEmpresa) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.\"TipoEmpresa\"\n" +
                    "\tSET estado=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, tipoEmpresa.getEstado());
            ps.setInt(2, tipoEmpresa.getId());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public List<TipoEmpresa> listTipoEmpresaActivo() throws SQLException {
        List<TipoEmpresa> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abreviatura, estado, \"Cfecha\"\n" +
                    "\tFROM public.\"TipoEmpresa\" where estado = 'Activo';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TipoEmpresa te = new TipoEmpresa();
                te.setId(rs.getInt(1));
                te.setNombre(rs.getString(2));
                te.setAbreviatura(rs.getString(3));
                te.setEstado(rs.getString(4));
                te.setCfecha(rs.getTimestamp(5));
                lst.add(te);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return  lst;
    }
}

