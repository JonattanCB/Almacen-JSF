package org.TopAlmacen.Almacen.GestionTipoDocumento.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionRol.dto.Rol;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dto.TipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.servicio.ServicioTipoDocumento;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class BeansGestionTipoDocumento implements Serializable {

    @Inject
    private ServicioTipoDocumento  servicioTipoDocumento;

    private TipoDocumento tipoDocumento;
    private List<TipoDocumento> list_tdocumento;
    private List<TipoDocumento> list_tdocumentoSeleccionado;
    private int ID_TipoDocumento;

    public void abrirNuevoTipoDocumento() {
        this.tipoDocumento = new TipoDocumento();
    }

    public void abrirtipoDocumento () throws SQLException {
        tipoDocumento = servicioTipoDocumento.buscarTipoDocumento(ID_TipoDocumento);
    }

    public void guardarTipoDocumento() throws SQLException {
        if (tipoDocumento.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            tipoDocumento.setEstado("1");
            tipoDocumento.setCfecha(timestamp);
            servicioTipoDocumento.guardarTipoDocumento(tipoDocumento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de documento Agregado"));
        }else{
            servicioTipoDocumento.ActualizarTipoDocumento(tipoDocumento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de documento Actualizado"));
        }
        list_tdocumento = servicioTipoDocumento.listaTipoDocumento();
        PrimeFaces.current().executeScript("PF('tddialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-td");
    }

    public String irTipoDocuento() throws SQLException {
        list_tdocumento = servicioTipoDocumento.listaTipoDocumento();
        return  "gestionpersonal/tipoDocumento";
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        System.out.println(filterText);
        int filterInt = getInteger(filterText);
        TipoDocumento td = (TipoDocumento) value;
        return  (td.getId()>=filterInt && td.getId()<=filterInt)
                ||td.getNombre().toLowerCase().contains(filterText)
                || td.getDescripcion().toLowerCase().contains(filterText);
    }

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }


    
    
    

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public List<TipoDocumento> getList_tdocumento() {
        return list_tdocumento;
    }

    public void setList_tdocumento(List<TipoDocumento> list_tdocumento) {
        this.list_tdocumento = list_tdocumento;
    }

    public List<TipoDocumento> getList_tdocumentoSeleccionado() {
        return list_tdocumentoSeleccionado;
    }

    public void setList_tdocumentoSeleccionado(List<TipoDocumento> list_tdocumentoSeleccionado) {
        this.list_tdocumentoSeleccionado = list_tdocumentoSeleccionado;
    }

    public int getID_TipoDocumento() {
        return ID_TipoDocumento;
    }

    public void setID_TipoDocumento(int ID_TipoDocumento) {
        this.ID_TipoDocumento = ID_TipoDocumento;
    }
}
