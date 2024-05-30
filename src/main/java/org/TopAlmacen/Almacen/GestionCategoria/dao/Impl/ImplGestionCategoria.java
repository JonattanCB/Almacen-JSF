package org.TopAlmacen.Almacen.GestionCategoria.dao.Impl;

import org.TopAlmacen.Almacen.GestionCategoria.dao.daoGestionCategoria;
import org.TopAlmacen.Almacen.GestionCategoria.dto.Categoria;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ImplGestionCategoria implements daoGestionCategoria {

    private final conexion con = new conexion();

    @Override
    public void registrar(Categoria categoria) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.\"Categoria\"(\n" +
                    "\t nombre, descripcion, estado, cfecha)\n" +
                    "\tVALUES ( ?, ?, ?, ?);");
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setString(3,categoria.getEstado());
            ps.setTimestamp(4, categoria.getCFecha());
            ps.execute();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Categoria categoria) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.\"Categoria\"\n" +
                    "\tSET  nombre=?, descripcion=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getId());
            ps.execute();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(Categoria categoria) throws SQLException {

    }

    @Override
    public Categoria buscar(int t) throws SQLException {
        Categoria c = new Categoria();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, descripcion, estado, cfecha\n" +
                    "\tFROM public.\"Categoria\" where id = ?;");
            ps.setInt(1,t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setDescripcion(rs.getString(3));
                c.setEstado(rs.getString(4));
                c.setCFecha(rs.getTimestamp(5));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }

        return c;
    }

    @Override
    public List<Categoria> buscarTodos() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, descripcion, estado, cfecha\n" +
                    "\tFROM public.\"Categoria\" ;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Categoria c = new Categoria();
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setDescripcion(rs.getString(3));
                c.setEstado(rs.getString(4));
                c.setCFecha(rs.getTimestamp(5));
                categorias.add(c);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }

        return categorias;
    }
}
