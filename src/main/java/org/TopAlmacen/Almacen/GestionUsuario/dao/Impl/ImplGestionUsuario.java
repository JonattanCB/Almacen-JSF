package org.TopAlmacen.Almacen.GestionUsuario.dao.Impl;

import org.TopAlmacen.Almacen.GestionPersona.dao.Impl.ImplGestionPersona;
import org.TopAlmacen.Almacen.GestionPersona.dao.daoGestionPersona;
import org.TopAlmacen.Almacen.GestionRol.dao.daoGestionRol;
import org.TopAlmacen.Almacen.GestionRol.dao.impl.ImplGestionRol;
import org.TopAlmacen.Almacen.GestionUsuario.dao.daoGestionUsuario;
import org.TopAlmacen.Almacen.GestionUsuario.model.Usuario;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionUsuario implements daoGestionUsuario {

    private final conexion con = new conexion();
    private final daoGestionRol daorol = new ImplGestionRol();
    private final daoGestionPersona daopersonal = new ImplGestionPersona();


    @Override
    public void registrar(Usuario usuario) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.usuario(\n" +
                    "\t correo, contrasenia, rol_id, persona_id, estado, \"Cfecha\")\n" +
                    "\tVALUES ( ?, ?, ?, ?, ?, ?);");
            ps.setString(1,usuario.getCorreo());
            ps.setString(2,usuario.getContrasenia());
            ps.setInt(3, usuario.getRol().getId());
            ps.setInt(4, usuario.getPersonas().getId());
            ps.setString(5, usuario.getEstado());
            ps.setTimestamp(6,usuario.getCfecha());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.usuario\n" +
                    "\tSET correo=?, contrasenia=?, rol_id=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getContrasenia());
            ps.setInt(3,usuario.getRol().getId());
            ps.setInt(4, usuario.getId());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(Usuario usuario) throws SQLException {

    }

    @Override
    public Usuario buscar(int t) throws SQLException {
        Usuario  u = new Usuario();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, correo, contrasenia, rol_id, persona_id, estado, \"Cfecha\"\n" +
                    "\tFROM public.usuario where id =?;");
            ps.setInt(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                u.setId(rs.getInt(1));
                u.setCorreo(rs.getString(2));
                u.setContrasenia(rs.getString(3));
                u.setRol(daorol.buscar(rs.getInt(4)));
                u.setPersonas(daopersonal.buscar(rs.getInt(5)));
                u.setEstado(rs.getString(6));
                u.setCfecha(rs.getTimestamp(7));
            }
        }catch (SQLException e){
            System.err.println(e);
        }finally {
            con.cerrar();
        }
        return  u;
    }

    @Override
    public List<Usuario> buscarTodos() throws SQLException {
        List<Usuario> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, correo, contrasenia, rol_id, persona_id, estado, \"Cfecha\"\n" +
                    "\tFROM public.usuario order by id;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Usuario u = new Usuario();
                u.setId(rs.getInt(1));
                u.setCorreo(rs.getString(2));
                u.setContrasenia(rs.getString(3));
                u.setRol(daorol.buscar(rs.getInt(4)));
                u.setPersonas(daopersonal.buscar(rs.getInt(5)));
                u.setEstado(rs.getString(6));
                u.setCfecha(rs.getTimestamp(7));
                lst.add(u);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return lst;
    }
}
