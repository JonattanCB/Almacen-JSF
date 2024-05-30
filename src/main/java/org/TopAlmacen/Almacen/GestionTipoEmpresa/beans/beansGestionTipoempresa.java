package org.TopAlmacen.Almacen.GestionTipoEmpresa.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.dto.TipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.servicio.ServicioGestionTipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoUnidad.dto.TipoUnidad;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class beansGestionTipoempresa implements Serializable {

    @Inject
    private ServicioGestionTipoEmpresa servicioGestionTipoEmpresa;


    private List<TipoEmpresa> lstTabla;
    private List<TipoEmpresa> lstSeleccionado;
    private TipoEmpresa tipoEmpresa;
    private int id_seleccionada;

    public String irTipoEmpresa() throws SQLException {
        lstTabla = servicioGestionTipoEmpresa.lstTodo();
        return "gestionProducto/TipoEmpresa";
    }

    public void nuevaTipoUnidad(){
        tipoEmpresa = new TipoEmpresa();
    }

    public void ireditar() throws SQLException {
        tipoEmpresa = servicioGestionTipoEmpresa.buscar(id_seleccionada);
    }

    public void  guardar() throws SQLException {
        if (tipoEmpresa.getId() == 0){
            servicioGestionTipoEmpresa.registrar(tipoEmpresa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo empresa Agregado"));
        }else{
            servicioGestionTipoEmpresa.modificar(tipoEmpresa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo empresa Actualizado"));
        }
        lstTabla = servicioGestionTipoEmpresa.lstTodo();
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        TipoEmpresa c = (TipoEmpresa) value;
        return  (c.getId()>=filterInt && c.getId()<=filterInt)
                ||c.getNombre().toLowerCase().contains(filterText)
                || c.getAbreviatura().toLowerCase().contains(filterText);
    }

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    public ServicioGestionTipoEmpresa getServicioGestionTipoEmpresa() {
        return servicioGestionTipoEmpresa;
    }

    public void setServicioGestionTipoEmpresa(ServicioGestionTipoEmpresa servicioGestionTipoEmpresa) {
        this.servicioGestionTipoEmpresa = servicioGestionTipoEmpresa;
    }

    public List<TipoEmpresa> getLstTabla() {
        return lstTabla;
    }

    public void setLstTabla(List<TipoEmpresa> lstTabla) {
        this.lstTabla = lstTabla;
    }

    public List<TipoEmpresa> getLstSeleccionado() {
        return lstSeleccionado;
    }

    public void setLstSeleccionado(List<TipoEmpresa> lstSeleccionado) {
        this.lstSeleccionado = lstSeleccionado;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public int getId_seleccionada() {
        return id_seleccionada;
    }

    public void setId_seleccionada(int id_seleccionada) {
        this.id_seleccionada = id_seleccionada;
    }

}
