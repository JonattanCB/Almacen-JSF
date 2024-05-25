package org.TopAlmacen.Almacen.GestionJefesArea.dao.impl;

import  org.TopAlmacen.Almacen.GestionJefesArea.dao.daoGestionJefeArea;
import org.TopAlmacen.Almacen.GestionJefesArea.dto.JefesArea;
import org.TopAlmacen.Almacen.Utils.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplGestionJefeArea implements  daoGestionJefeArea {

    private final conexion cxn = new conexion();

    @Override
    public List<JefesArea> listartodo() throws SQLException {
        List<JefesArea> lst = new ArrayList<>();
        try {
            PreparedStatement ps = cxn.crearCNX().prepareStatement("SELECT personas.id , personas.pnombre, personas.snombre , personas.apaterno , personas.amaterno,\n" +
                    "\tarea.nombre, personas.telefono\n" +
                    "FROM public.\"JefeArea\"\n" +
                    "INNER JOIN public.area ON area.id = \"JefeArea\".area_id\n" +
                    "INNER JOIN public.personas ON personas.id = \"JefeArea\".persona_id ");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                JefesArea ja = new JefesArea();
                ja.setId(rs.getInt(1));
                ja.setNombre(rs.getString(2)+ " "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
                ja.setArea(rs.getString(6));
                ja.setNumero(rs.getInt(7));
                lst.add(ja);
            }

        }catch (Exception e){
            System.err.println("Error: "+e);
        }finally {
            cxn.cerrar();
        }
        return lst;
    }
}
