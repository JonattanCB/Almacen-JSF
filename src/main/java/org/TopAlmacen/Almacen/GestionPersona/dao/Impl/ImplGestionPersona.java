package org.TopAlmacen.Almacen.GestionPersona.dao.Impl;

import org.TopAlmacen.Almacen.GestionPersona.dao.daoGestionPersona;
import org.TopAlmacen.Almacen.GestionPersona.model.Persona;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.Impl.ImplGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.daoGestionTipoDocumento;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ImplGestionPersona implements daoGestionPersona {

    private final conexion con = new conexion();
    private final daoGestionTipoDocumento daotd = new ImplGestionTipoDocumento();

    @Override
    public void registrar(Persona persona) throws SQLException {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.personas(\n" +
                    "\tpnombre, snombre, apaterno, amaterno, tdocumento, ndocumento, direccion, telefono, estado)\n" +
                    "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1,persona.getPnombre());
            ps.setString(2,persona.getSnombre());
            ps.setString(3,persona.getApatero());
            ps.setString(4,persona.getAmatero());
            ps.setInt(5, persona.getTipoDocumento().getId());
            ps.setInt(6, persona.getNdocumento());
            ps.setString(7, persona.getDireccion());
            ps.setString(8, persona.getEstado());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void actualizar(Persona persona) throws SQLException {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.personas\n" +
                    "\tSET  pnombre=?, snombre=?, apaterno=?, amaterno=?, tdocumento=?, ndocumento=?, direccion=?, telefono=? \n" +
                    "\tWHERE id=?;");
            ps.setString(1,persona.getPnombre());
            ps.setString(2,persona.getSnombre());
            ps.setString(3,persona.getApatero());
            ps.setString(4,persona.getAmatero());
            ps.setInt(5, persona.getTipoDocumento().getId());
            ps.setInt(6, persona.getNdocumento());
            ps.setString(7, persona.getDireccion());
            ps.setInt(8, persona.getTelefono());
            ps.setInt(9,persona.getId());
            ps.execute();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public void eliminar(Persona persona) throws SQLException {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("");

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
    }

    @Override
    public Persona buscar(int t) throws SQLException {
        Persona p = new Persona();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, pnombre, snombre, apaterno, amaterno, tdocumento, ndocumento, direccion, telefono, estado\n" +
                    "\tFROM public.personas where id=?;");
            ps.setInt(1, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                p.setId(rs.getInt(1));
                p.setPnombre(rs.getString(2));
                p.setSnombre(rs.getString(3));
                p.setApatero(rs.getString(4));
                p.setAmatero(rs.getString(5));
                p.setTipoDocumento(daotd.buscar(rs.getInt(6)));
                p.setNdocumento(rs.getInt(7));
                p.setDireccion(rs.getString(8));
                p.setTelefono(rs.getInt(9));
                p.setEstado(rs.getString(10));
            }
        }catch (SQLException e){
            System.err.println(e);
        }finally {
            con.cerrar();
        }
        return p;
    }

    @Override
    public List<Persona> buscarTodos() throws SQLException {
        return List.of();
    }

    @Override
    public int registraPersonaRetornaID(Persona persona) throws SQLException {
        int id = 0;
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.personas(\n" +
                    "\tpnombre, snombre, apaterno, amaterno, tdocumento, ndocumento, direccion, telefono, estado)\n" +
                    "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id ;");
            ps.setString(1,persona.getPnombre());
            ps.setString(2,persona.getSnombre());
            ps.setString(3,persona.getApatero());
            ps.setString(4,persona.getAmatero());
            ps.setInt(5, persona.getTipoDocumento().getId());
            ps.setInt(6, persona.getNdocumento());
            ps.setString(7, persona.getDireccion());
            ps.setInt(8, persona.getTelefono());
            ps.setString(9, "1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return id;
    }
}
