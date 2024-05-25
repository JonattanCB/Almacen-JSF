package org.TopAlmacen.Almacen.Utils;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class conexion {
    Connection con = null;
    public Connection crearCNX(){
        try {
            InitialContext conex = new InitialContext();
            DataSource ds = (DataSource) conex.lookup("java:/almacen");
            con = ds.getConnection();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return con;
    }

    public void cerrar() throws SQLException {
        if (con != null) {
            if (!con.isClosed()){
                con.close();
            }
        }
    }

}
