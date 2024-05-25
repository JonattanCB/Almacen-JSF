package org.TopAlmacen.Almacen.GestionJefesArea.dao;

import org.TopAlmacen.Almacen.GestionJefesArea.dto.JefesArea;

import java.sql.SQLException;
import java.util.List;

public interface daoGestionJefeArea {
    List<JefesArea> listartodo() throws SQLException;

}
