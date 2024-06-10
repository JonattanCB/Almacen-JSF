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
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
@Data
public class beansGestionTipoUnidad implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioGestionTipoUnidad servicioGestionTipoUnidad;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */
    private List<TipoUnidad> lstTabla;
    private List<TipoUnidad> lstSeleccionado;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private TipoUnidad tipoUnidad;
    private int id_seleccionada;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init(){
        try {
            lstTabla = servicioGestionTipoUnidad.lstTipoUnidad();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */
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

    public void CambiarEstado() throws  SQLException{
        tipoUnidad = servicioGestionTipoUnidad.buscar(id_seleccionada);
        switch (tipoUnidad.getEstado()){
            case "Activo":
                tipoUnidad.setEstado("Inactivo");
                break;
            case "Inactivo":
                tipoUnidad.setEstado("Activo");
                break;
        }
        servicioGestionTipoUnidad.CambiarEstado(tipoUnidad);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado del tipo de unidad ha cambiado a "+tipoUnidad.getEstado()+"!"));
        lstTabla = servicioGestionTipoUnidad.lstTipoUnidad();
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public void  guardar() throws SQLException {
        if (tipoUnidad.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            tipoUnidad.setEstado("Activo");
            tipoUnidad.setCfecha(timestamp);
            servicioGestionTipoUnidad.registrar(tipoUnidad);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El tipo de unidad "+tipoUnidad.getNombre()+" ha sido registrado exitosamente en el sistema!"));
        }else{
            servicioGestionTipoUnidad.modificar(tipoUnidad);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El tipo de unidad "+tipoUnidad.getNombre()+" ha sido actualizado exitosamente en el sistema!"));
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

    /*  =========================== Extensiones  ========================= */
    /*  ================================================================== */
    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    /*  ================================================================== */
}
