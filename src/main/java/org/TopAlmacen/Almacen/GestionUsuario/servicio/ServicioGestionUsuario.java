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

    private final daoGestionUsuario dao;

    public ServicioGestionUsuario() {
        dao = new ImplGestionUsuario();
    }

    public List<Usuario> listaUsuario() throws SQLException {
        List<Usuario> lst = dao.buscarTodos();
        return lst;
    }

    public void registrarUsuario(Usuario u) throws SQLException {
        dao.registrar(u);
    }

    public Usuario buscarUsuario(int id) throws SQLException {
        return  dao.buscar(id);
    }

    public void editarUsuario(Usuario u) throws SQLException {
        dao.actualizar(u);
    }

    public boolean VerificiarUsuario(Usuario u) throws SQLException {
        return  dao.verificadorUsuario(u);
    }

    public void CambiarEstado(Usuario u) throws  SQLException{
        dao.cambiarEstado(u);
    }

    public boolean verificarUsuarioRepetido(Usuario u) throws SQLException {
        return  dao.verificarUsuarioRepetido(u);
    }

    public Usuario traerLoginUsuario(Usuario u) throws SQLException {
        return  dao.traerLoginUsuario(u);
    }

}
