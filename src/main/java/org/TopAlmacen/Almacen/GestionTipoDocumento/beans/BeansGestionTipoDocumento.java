package org.TopAlmacen.Almacen.GestionTipoDocumento.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionTipoDocumento.servicio.ServicioTipoDocumento;

import java.io.Serializable;

@Named
@SessionScoped
public class BeansGestionTipoDocumento implements Serializable {

    @Inject
    private ServicioTipoDocumento  servicioTipoDocumento;



}
