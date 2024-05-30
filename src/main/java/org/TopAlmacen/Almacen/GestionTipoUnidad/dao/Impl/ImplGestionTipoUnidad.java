package org.TopAlmacen.Almacen.GestionTipoUnidad.dao.Impl;

import org.TopAlmacen.Almacen.GestionTipoUnidad.dao.daoGestionTipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dto.TipoUnidad;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionTipoUnidad  implements daoGestionTipoUnidad {

    private conexion con = new conexion();

    @Override
    public void registrar(TipoUnidad tipoUnidad) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.\"TipoUnidad\"(\n" +
                    "\t nombre, abrev)\n" +
                    "\tVALUES ( ?, ?);");
            ps.setString(1, tipoUnidad.getNombre());
            ps.setString(2, tipoUnidad.getAbrev());
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
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abrev\n" +
                    "\tFROM public.\"TipoUnidad\" where id= ?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                tu.setId(rs.getInt(1));
                tu.setNombre(rs.getString(2));
                tu.setAbrev(rs.getString(3));
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
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, abrev\n" +
                    "\tFROM public.\"TipoUnidad\";");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TipoUnidad tu = new TipoUnidad();
                tu.setId(rs.getInt(1));
                tu.setNombre(rs.getString(2));
                tu.setAbrev(rs.getString(3));
                lst.add(tu);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return  lst;
    }
}
