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
            PreparedStatement ps = con.crearCNX().prepareStatement("");


        }catch (SQLException e){
            System.err.println(e);
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("");


        }catch (SQLException e){
            System.err.println(e);
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
            PreparedStatement ps = con.crearCNX().prepareStatement("");


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
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, usuario, contrasenia, rol_id, persona_id, estado, \"Cfecha\"\n" +
                    "\tFROM public.usuario order by id;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Usuario u = new Usuario();
                u.setId(rs.getInt(1));
                u.setCorreo(rs.getString(2));
                u.setContraseña(rs.getString(3));
                u.setRol(daorol.buscar(rs.getInt(4)));
                u.setPersonas(daopersonal.buscar(rs.getInt(5)));
                u.setEstado(rs.getString(6));
                u.setCfecha(rs.getTimestamp(7));
                lst.add(u);
            }
        }catch (SQLException e){
            System.err.println(e);
        }finally {
            con.cerrar();
        }
        return lst;
    }
}
