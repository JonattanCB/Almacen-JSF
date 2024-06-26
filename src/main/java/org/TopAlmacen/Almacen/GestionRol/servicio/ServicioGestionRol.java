package org.TopAlmacen.Almacen.GestionRol.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionRol.dao.daoGestionRol;
import org.TopAlmacen.Almacen.GestionRol.dao.impl.ImplGestionRol;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Stateless
@LocalBean
public class ServicioGestionRol implements Serializable {

    private final daoGestionRol dao;

    public ServicioGestionRol() {
        dao = new ImplGestionRol();
    }

    public List<Rol> listarRol() throws SQLException {
        List<Rol> lst = dao.buscarTodos();
        return lst;
    }

    public void insertarRol(Rol rol) throws SQLException  {
        dao.registrar(rol);
    }

    public void modificarRol(Rol rol) throws SQLException  {
        dao.actualizar(rol);
    }

    public Rol buscarRol(int id) throws SQLException  {
        return dao.buscar(id);
    }

    public void CambiarEstado(Rol rol) throws SQLException  {
        dao.cambioEstado(rol);
    }

    public List<Rol> listRolActivo() throws  SQLException{
        return  dao.listaRolActivo();
    }

}
