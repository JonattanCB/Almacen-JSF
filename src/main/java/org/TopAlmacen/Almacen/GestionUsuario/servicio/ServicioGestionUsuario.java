package org.TopAlmacen.Almacen.GestionUsuario.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionUsuario.dao.Impl.ImplGestionUsuario;
import org.TopAlmacen.Almacen.GestionUsuario.dao.daoGestionUsuario;
import org.TopAlmacen.Almacen.GestionUsuario.model.Usuario;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioGestionUsuario implements Serializable {

    private final daoGestionUsuario dao = new ImplGestionUsuario();

    public List<Usuario> listaUsuario() throws SQLException {
        List<Usuario> lst = dao.buscarTodos();
        return lst;
    }

}
