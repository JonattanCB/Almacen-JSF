package org.TopAlmacen.Almacen.GestionTipoDocumento.dao.Impl;

import org.TopAlmacen.Almacen.GestionRol.model.Rol;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.daoGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionTipoDocumento implements daoGestionTipoDocumento {

    private final conexion con = new conexion();

    @Override
    public void registrar(TipoDocumento tipoDocumento) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.tipodocumento(\n" +
                    "\tnombre, descripcion, estado, \"CFecha\")\n" +
                    "\tVALUES ( ?, ?, ?, ?);");
            ps.setString(1, tipoDocumento.getNombre());
            ps.setString(2, tipoDocumento.getDescripcion());
            ps.setString(3, tipoDocumento.getEstado());
            ps.setTimestamp(4,tipoDocumento.getCfecha());
            ps.execute();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(TipoDocumento tipoDocumento) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.tipodocumento\n" +
                    "\tSET  nombre=?, descripcion=?\n" +
                    "\tWHERE id =?;");
            ps.setString(1, tipoDocumento.getNombre());
            ps.setString(2, tipoDocumento.getDescripcion());
            ps.setInt(3, tipoDocumento.getId());
            ps.execute();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(TipoDocumento tipoDocumento) throws SQLException {
        try {

        }catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public TipoDocumento buscar(int t) throws SQLException {
        TipoDocumento td = new TipoDocumento();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, descripcion\n" +
                    "\tFROM public.tipodocumento where id =?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                td.setId(rs.getInt(1));
                td.setNombre(rs.getString(2));
                td.setDescripcion(rs.getString(3));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return td;
    }

    @Override
    public List<TipoDocumento> buscarTodos() throws SQLException {
        List<TipoDocumento> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT * FROM public.tipodocumento\n" +
                    "ORDER BY id ASC ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TipoDocumento td = new TipoDocumento();
                td.setId(rs.getInt(1));
                td.setNombre(rs.getString(2));
                td.setDescripcion(rs.getString(3));
                td.setEstado(rs.getString(4));
                td.setCfecha(rs.getTimestamp(5));
                lst.add(td);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return  lst;
    }
}