package org.TopAlmacen.Almacen.GestionTipoDocumento.dao.Impl;

import org.TopAlmacen.Almacen.GestionTipoDocumento.dao.daoGestionTipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dto.TipoDocumento;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionTipoDocumento implements daoGestionTipoDocumento {

    private conexion con;

    @Override
    public void registrar(TipoDocumento tipoDocumento) throws SQLException {

    }

    @Override
    public void actualizar(TipoDocumento tipoDocumento) throws SQLException {

    }

    @Override
    public void eliminar(TipoDocumento tipoDocumento) throws SQLException {

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
        List<TipoDocumento> tipoDocumentos = new ArrayList<TipoDocumento>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT * FROM public.tipodocumento\n" +
                    "ORDER BY id ASC ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TipoDocumento tipoDocumento = new TipoDocumento();
                tipoDocumento.setId(rs.getInt(1));
                tipoDocumento.setNombre(rs.getString(2));
                tipoDocumento.setDescripcion(rs.getString(3));
                tipoDocumentos.add(tipoDocumento);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            con.cerrar();
        }
        return tipoDocumentos;
    }
}
