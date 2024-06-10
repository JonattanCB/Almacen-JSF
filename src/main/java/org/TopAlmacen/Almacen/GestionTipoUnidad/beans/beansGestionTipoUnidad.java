package org.TopAlmacen.Almacen.GestionTipoUnidad.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionTipoUnidad.model.TipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.servicio.ServicioGestionTipoUnidad;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
@Data
public class beansGestionTipoUnidad implements Serializable {

    @Inject
    private ServicioGestionTipoUnidad servicioGestionTipoUnidad;

    private List<TipoUnidad> lstTabla;
    private List<TipoUnidad> lstSeleccionado;
    private TipoUnidad tipoUnidad;
    private int id_seleccionada;

    @PostConstruct
    public void init(){
        try {
            lstTabla = servicioGestionTipoUnidad.lstTipoUnidad();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

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
}
