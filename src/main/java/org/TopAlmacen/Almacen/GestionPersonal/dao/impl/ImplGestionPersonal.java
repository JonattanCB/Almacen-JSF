package org.TopAlmacen.Almacen.GestionPersonal.dao.impl;

import org.TopAlmacen.Almacen.GestionPersonal.dao.daoGestionPersonal;
import org.TopAlmacen.Almacen.GestionPersonal.dto.Personas;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.Impl.ImplGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.daoGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dto.TipoDocumento;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionPersonal implements daoGestionPersonal {

    private conexion con;
    private daoGestionTipoDocumento daotd = new ImplGestionTipoDocumento();

    @Override
    public void registrar(Personas personas) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.personas(\n" +
                    "\t pnombre, snombre, apaterno, amaterno, tdocumento, ndocumento, direccion, telefono, estado)\n" +
                    "\tVALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1,personas.getPnombre());
            ps.setString(2,personas.getSnombre());
            ps.setString(3, personas.getApaterno());
            ps.setString(4, personas.getAmaterno());
            ps.setInt(5,personas.getTipoDocumento().getId());
            ps.setInt(6,personas.getN_documento());
            ps.setString(7, personas.getDireccion());
            ps.setInt(8, personas.getTelefono());
            ps.setString(9, personas.getEstado());
            ps.execute();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Personas personas) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.personas\n" +
                    "\tSET pnombre=?, snombre=?, apaterno=?, amaterno=?, tdocumento=?, ndocumento=?, direccion=?, telefono=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, personas.getPnombre());
            ps.setString(2, personas.getSnombre());
            ps.setString(3, personas.getApaterno());
            ps.setString(4, personas.getAmaterno());
            ps.setInt(5, personas.getTipoDocumento().getId());
            ps.setInt(6, personas.getN_documento());
            ps.setString(7, personas.getDireccion());
            ps.setInt(8, personas.getTelefono());
            ps.setInt(9, personas.getId());
            ps.execute();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(Personas personas) throws SQLException {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("");

        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public Personas buscar(int t) throws SQLException {
        Personas p = new Personas();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, pnombre, snombre, apaterno, amaterno, tdocumento, ndocumento, direccion, telefono, estado\n" +
                    "\tFROM public.personas where id=?; ");
            ps.setInt(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                p.setId(rs.getInt(1));
                p.setPnombre(rs.getString(2));
                p.setSnombre(rs.getString(3));
                p.setApaterno(rs.getString(4));
                p.setAmaterno(rs.getString(5));
                TipoDocumento td = daotd.buscar(rs.getInt(6));
                p.setTipoDocumento(td);
                p.setN_documento(rs.getInt(7));
                p.setDireccion(rs.getString(8));
                p.setEstado(rs.getString(9));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return p;
    }

    @Override
    public List<Personas> buscarTodos() throws SQLException {
        List<Personas> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, pnombre, snombre, apaterno, amaterno, tdocumento, ndocumento, direccion, telefono, estado\n" +
                    "\tFROM public.personas ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Personas p = new Personas();
                p.setId(rs.getInt(1));
                p.setPnombre(rs.getString(2));
                p.setSnombre(rs.getString(3));
                p.setApaterno(rs.getString(4));
                p.setAmaterno(rs.getString(5));
                TipoDocumento td = daotd.buscar(rs.getInt(6));
                p.setTipoDocumento(td);
                p.setN_documento(rs.getInt(7));
                p.setDireccion(rs.getString(8));
                p.setEstado(rs.getString(9));
                lst.add(p);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return lst;
    }
}
