package org.TopAlmacen.Almacen.GestionCategoria.beans;


import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionCategoria.Servicio.ServicioGestionCategoria;
import org.TopAlmacen.Almacen.GestionCategoria.dto.Categoria;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dto.TipoDocumento;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class beansGestionCategoria implements Serializable {

    @Inject
    private ServicioGestionCategoria servicioGestionCategoria;

    private List<Categoria> lstTabla;
    private List<Categoria> lstSeleccionado;
    private Categoria categoria;
    private int id_seleccionada;

    public String irCategoria() throws SQLException {
        lstTabla = servicioGestionCategoria.lstCategoria();
        return "gestionProducto/categoria";
    }

    public void nuevaCategoria(){
        categoria = new Categoria();
    }

    public void editarCategoria() throws SQLException {
        categoria = servicioGestionCategoria.buscarCategoria(id_seleccionada);
    }

    public void  guardar() throws SQLException {
        if (categoria.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            categoria.setCFecha(timestamp);
            categoria.setEstado("1");
            servicioGestionCategoria.registrarCategoria(categoria);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria Agregado"));
        }else{
            servicioGestionCategoria.actualizar(categoria);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria Actualizado"));
        }
        lstTabla = servicioGestionCategoria.lstCategoria();
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        Categoria c = (Categoria) value;
        return  (c.getId()>=filterInt && c.getId()<=filterInt)
                ||c.getNombre().toLowerCase().contains(filterText)
                || c.getDescripcion().toLowerCase().contains(filterText);
    }

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }



    public List<Categoria> getLstTabla() {
        return lstTabla;
    }

    public void setLstTabla(List<Categoria> lstTabla) {
        this.lstTabla = lstTabla;
    }

    public List<Categoria> getLstSeleccionado() {
        return lstSeleccionado;
    }

    public void setLstSeleccionado(List<Categoria> lstSeleccionado) {
        this.lstSeleccionado = lstSeleccionado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getId_seleccionada() {
        return id_seleccionada;
    }

    public void setId_seleccionada(int id_seleccionada) {
        this.id_seleccionada = id_seleccionada;
    }
}
