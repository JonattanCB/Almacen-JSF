package org.TopAlmacen.Almacen.GestionJefesArea.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

import jakarta.enterprise.context.RequestScoped;
import  org.TopAlmacen.Almacen.GestionJefesArea.dao.daoGestionJefeArea;
import org.TopAlmacen.Almacen.GestionJefesArea.dao.impl.ImplGestionJefeArea;
import org.TopAlmacen.Almacen.GestionJefesArea.dto.JefesArea;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class servicioGestionJefeArea implements Serializable {

    private daoGestionJefeArea Dao = new ImplGestionJefeArea();

    public List<JefesArea> listarTodo() throws SQLException {
        return  Dao.listartodo();
    }




}
