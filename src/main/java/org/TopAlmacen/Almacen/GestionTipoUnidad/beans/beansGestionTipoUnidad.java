package org.TopAlmacen.Almacen.GestionTipoUnidad.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dto.TipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.servicio.servicioGestionTipoUnidad;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Named
@SessionScoped
public class beansGestionTipoUnidad implements Serializable {

    @Inject
    private servicioGestionTipoUnidad servicioGestionTipoUnidad;

    private List<TipoUnidad> lsttabla;
    private List<TipoUnidad> lstSeleciones;
    private TipoUnidad tipoUnidad;

    public String irTipoUnidad() throws SQLException {
        lsttabla = servicioGestionTipoUnidad.lstTipoUnidad();
        return "gestionProducto/categoria";
    }





}
