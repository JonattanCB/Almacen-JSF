package org.TopAlmacen.Almacen.GestionRol.dao.impl;

import org.TopAlmacen.Almacen.GestionRol.dao.daoGestionRol;
import org.TopAlmacen.Almacen.GestionRol.dto.Rol;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionRol implements daoGestionRol {

    private final conexion conn = new conexion();

    @Override
    public void registrar(Rol rol) throws SQLException {
        try {
            PreparedStatement ps = conn.crearCNX().prepareStatement("INSERT INTO public.rol(\n" +
                    "\t nombre, descripcion, estado, \"Cfecha\")\n" +
                    "\tVALUES (?, ?, ?, ?);");
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.setString(3, rol.getEstado());
            ps.setTimestamp(4,rol.getCfecha());
            ps.executeQuery();
        }catch (Exception e){
            System.err.println("Error" +e);
        }finally {
            conn.cerrar();
        }
    }

    @Override
    public void actualizar(Rol rol) throws SQLException{
        try {
            PreparedStatement ps = conn.crearCNX().prepareStatement("UPDATE public.rol\n" +
                    "\tSET nombre=?, descripcion=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.setInt(3, rol.getId());
            ps.executeQuery();
        }catch (Exception e){
            System.err.println("Error" +e);
        }finally {
            conn.cerrar();
        }
    }

    @Override
    public void eliminar(Rol rol) throws SQLException {

    }

    @Override
    public Rol buscar(int rol) throws SQLException {
        Rol rol1 = new Rol();
        try {
            PreparedStatement ps = conn.crearCNX().prepareStatement("SELECT id, nombre, descripcion, estado, \"Cfecha\"\n" +
                    "\tFROM public.rol WHERE id =?;");
            ps.setInt(1,rol);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rol1.setId(rs.getInt(1));
                rol1.setNombre(rs.getString(2));
                rol1.setDescripcion(rs.getString(3));
                rol1.setEstado(rs.getString(4));
                rol1.setCfecha(rs.getTimestamp(5));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            conn.cerrar();
        }
        return rol1;
    }

    @Override
    public List<Rol> buscarTodos() throws SQLException {
        List<Rol> buscarTodos = new ArrayList<>();
        try {
            PreparedStatement ps = conn.crearCNX().prepareStatement("SELECT id, nombre, descripcion, estado, \"Cfecha\"\n" +
                    "\tFROM public.rol ORDER by id ASC;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId(rs.getInt(1));
                rol.setNombre(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
                rol.setEstado(rs.getString(4));
                rol.setCfecha(rs.getTimestamp(5));
                buscarTodos.add(rol);
            }
        }catch (Exception e){
            System.err.println("Error" +e);
        }finally {
            conn.cerrar();
        }
        return buscarTodos;
    }

}
