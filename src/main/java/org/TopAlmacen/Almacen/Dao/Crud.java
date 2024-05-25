package org.TopAlmacen.Almacen.Dao;

import java.sql.SQLException;
import java.util.List;

public interface Crud<T>{
    void registrar(T t) throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(T t) throws SQLException;
    T buscar(int t) throws SQLException;
    List<T> buscarTodos() throws SQLException;
}
