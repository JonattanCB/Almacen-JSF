package org.TopAlmacen.Almacen.GestionTipoUnidad.dao.Impl;

import org.TopAlmacen.Almacen.GestionTipoUnidad.dao.daoGestionTipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.model.TipoUnidad;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionTipoUnidad  implements daoGestionTipoUnidad {

    private final conexion con;

    public ImplGestionTipoUnidad() {
        con = new conexion();
    }

    @Override
    public void registrar(TipoUnidad tipoUnidad) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.\"TipoUnidad\"(\n" +
                    "\t, nombre, abrev, estado, \"Cfecha\")\n" +
                    "\tVALUES ( ?, ?, ?, ?);");
            ps.setString(1, tipoUnidad.getNombre());
            ps.setString(2, tipoUnidad.getAbrev());
            ps.setString(3,tipoUnidad.getEstado());
            ps.setTimestamp(4, tipoUnidad.getCfecha());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(TipoUnidad tipoUnidad) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.\"TipoUnidad\"\n" +
                    "\tSET  nombre=?, abrev=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, tipoUnidad.getNombre());
            ps.setString(2, tipoUnidad.getAbrev());
            ps.setInt(3, tipoUnidad.getId());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(TipoUnidad tipoUnidad) throws SQLException {
    }

    @Override
    public TipoUnidad buscar(int t) throws SQLException {
        TipoUnidad tu = new TipoUnidad();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abrev, estado, \"Cfecha\"\n" +
                    "\tFROM public.\"TipoUnidad\" where id = ?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                tu.setId(rs.getInt(1));
                tu.setNombre(rs.getString(2));
                tu.setAbrev(rs.getString(3));
                tu.setEstado(rs.getString(4));
                tu.setCfecha(rs.getTimestamp(5));
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return  tu;
    }

    @Override
    public List<TipoUnidad> buscarTodos() throws SQLException {
        List<TipoUnidad> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abrev, estado, \"Cfecha\"\n" +
                    "\tFROM public.\"TipoUnidad\" order by id asc;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TipoUnidad tu = new TipoUnidad();
                tu.setId(rs.getInt(1));
                tu.setNombre(rs.getString(2));
                tu.setAbrev(rs.getString(3));
                tu.setEstado(rs.getString(4));
                tu.setCfecha(rs.getTimestamp(5));
                lst.add(tu);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return  lst;
    }

    @Override
    public void cambiarestado(TipoUnidad tipoUnidad) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.\"TipoUnidad\"\n" +
                    "\tSET  estado=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, tipoUnidad.getEstado());
            ps.setInt(2, tipoUnidad.getId());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }
}
