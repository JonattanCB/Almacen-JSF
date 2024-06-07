package org.TopAlmacen.Almacen.GestionUsuario.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionUsuario.dao.Impl.ImplGestionPersona;
import org.TopAlmacen.Almacen.GestionUsuario.dao.daoGestionPersona;
import org.TopAlmacen.Almacen.GestionUsuario.model.Persona;

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
