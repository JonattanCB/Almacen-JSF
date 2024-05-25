package org.TopAlmacen.Almacen.GestionUsuario.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionUsuario.servicio.ServicioGestionUsuario;

import java.io.Serializable;

@Named
@SessionScoped
public class BeansGestionUsuario implements Serializable {

    @Inject
    private ServicioGestionUsuario servicioGestionUsuario;



}
