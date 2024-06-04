package org.TopAlmacen.Almacen.GestionPersona.Servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionPersona.dao.Impl.ImplGestionPersona;
import org.TopAlmacen.Almacen.GestionPersona.dao.daoGestionPersona;
import org.TopAlmacen.Almacen.GestionPersona.model.Persona;

import java.io.Serializable;
import java.sql.SQLException;

@Stateless
@LocalBean
public class ServicioPersona implements Serializable {

    private final daoGestionPersona dao = new ImplGestionPersona();

    public int registrarRetornadoID(Persona persona) throws SQLException {
        return  dao.registraPersonaRetornaID(persona);
    }

    public void editarPersona(Persona persona) throws SQLException {
        dao.actualizar(persona);
    }

}
