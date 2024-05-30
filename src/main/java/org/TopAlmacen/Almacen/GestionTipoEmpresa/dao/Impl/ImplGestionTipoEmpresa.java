package org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.Impl;

import org.TopAlmacen.Almacen.GestionTipoEmpresa.dao.daoTipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.dto.TipoEmpresa;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionTipoEmpresa implements daoTipoEmpresa {
    private conexion con = new conexion();

    @Override
    public void registrar(TipoEmpresa tipoEmpresa) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.\"TipoEmpresa\"(\n" +
                    "\t nombre, abreviatura)\n" +
                    "\tVALUES ( ?, ?);");
            ps.setString(1, tipoEmpresa.getNombre());
            ps.setString(2, tipoEmpresa.getAbreviatura());
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
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abreviatura\n" +
                    "\tFROM public.\"TipoEmpresa\" where id =?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                te.setId(rs.getInt(1));
                te.setNombre(rs.getString(2));
                te.setAbreviatura(rs.getString(3));
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
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abreviatura\n" +
                    "\tFROM public.\"TipoEmpresa\"");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TipoEmpresa te = new TipoEmpresa();
                te.setId(rs.getInt(1));
                te.setNombre(rs.getString(2));
                te.setAbreviatura(rs.getString(3));
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

