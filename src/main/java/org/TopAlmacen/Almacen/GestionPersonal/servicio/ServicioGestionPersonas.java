package org.TopAlmacen.Almacen.GestionPersonal.servicio;


import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionPersonal.dao.daoGestionPersonal;
import org.TopAlmacen.Almacen.GestionPersonal.dao.impl.ImplGestionPersonal;
import org.TopAlmacen.Almacen.GestionPersonal.dto.Personas;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioGestionPersonas  implements Serializable {

    private daoGestionPersonal dao = new ImplGestionPersonal();

    public List<Personas> personasList() throws SQLException {
        return  dao.buscarTodos();
    }

}
