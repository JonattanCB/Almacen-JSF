package org.TopAlmacen.Almacen.GestionTipoUnidad.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionCategoria.dto.Categoria;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dto.TipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.servicio.servicioGestionTipoUnidad;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class beansGestionTipoUnidad implements Serializable {

    @Inject
    private servicioGestionTipoUnidad servicioGestionTipoUnidad;

    private List<TipoUnidad> lstTabla;
    private List<TipoUnidad> lstSeleccionado;
    private TipoUnidad tipoUnidad;
    private int id_seleccionada;

    public String irTipoUnidad() throws SQLException {
        lstTabla = servicioGestionTipoUnidad.lstTipoUnidad();
        return "gestionProducto/TipoUnidad";
    }

    public void nuevaTipoUnidad(){
        tipoUnidad = new TipoUnidad();
    }

    public void ireditar() throws SQLException {
        tipoUnidad = servicioGestionTipoUnidad.buscar(id_seleccionada);
    }

    public void  guardar() throws SQLException {
        if (tipoUnidad.getId() == 0){
            servicioGestionTipoUnidad.registrar(tipoUnidad);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("TipoUnidad Agregado"));
        }else{
            servicioGestionTipoUnidad.modificar(tipoUnidad);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo Unidad Actualizado"));
        }
        lstTabla = servicioGestionTipoUnidad.lstTipoUnidad();
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        TipoUnidad c = (TipoUnidad) value;
        return  (c.getId()>=filterInt && c.getId()<=filterInt)
                ||c.getNombre().toLowerCase().contains(filterText)
                || c.getAbrev().toLowerCase().contains(filterText);
    }

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    public org.TopAlmacen.Almacen.GestionTipoUnidad.servicio.servicioGestionTipoUnidad getServicioGestionTipoUnidad() {
        return servicioGestionTipoUnidad;
    }

    public void setServicioGestionTipoUnidad(org.TopAlmacen.Almacen.GestionTipoUnidad.servicio.servicioGestionTipoUnidad servicioGestionTipoUnidad) {
        this.servicioGestionTipoUnidad = servicioGestionTipoUnidad;
    }

    public List<TipoUnidad> getLstTabla() {
        return lstTabla;
    }

    public void setLstTabla(List<TipoUnidad> lstTabla) {
        this.lstTabla = lstTabla;
    }

    public List<TipoUnidad> getLstSeleccionado() {
        return lstSeleccionado;
    }

    public void setLstSeleccionado(List<TipoUnidad> lstSeleccionado) {
        this.lstSeleccionado = lstSeleccionado;
    }

    public TipoUnidad getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(TipoUnidad tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public int getId_seleccionada() {
        return id_seleccionada;
    }

    public void setId_seleccionada(int id_seleccionada) {
        this.id_seleccionada = id_seleccionada;
    }
}
