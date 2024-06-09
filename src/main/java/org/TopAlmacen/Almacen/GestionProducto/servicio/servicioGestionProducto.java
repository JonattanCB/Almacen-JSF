package org.TopAlmacen.Almacen.GestionProducto.servicio;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import org.TopAlmacen.Almacen.GestionProducto.dao.daoGestionProducto;
import org.TopAlmacen.Almacen.GestionProducto.dao.impl.ImplGestionProducto;

import java.io.Serializable;

@Stateless
@LocalBean
public class servicioGestionProducto implements Serializable {

    private  final daoGestionProducto dao = new ImplGestionProducto();


}
