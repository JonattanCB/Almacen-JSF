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

    }

    @Override
    public void actualizar(Persona persona) throws SQLException {

    }

    @Override
    public void eliminar(Persona persona) throws SQLException {

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
}
