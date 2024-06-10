package org.TopAlmacen.Almacen.GestionTipoDocumento.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;
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
@Data
public class BeansGestionTipoDocumento implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioTipoDocumento  servicioTipoDocumento;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */
    private List<TipoDocumento> list_tdocumento;
    private List<TipoDocumento> list_tdocumentoSeleccionado;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private TipoDocumento tipoDocumento;
    private int ID_TipoDocumento;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init(){
        try {
            list_tdocumento = servicioTipoDocumento.listaTipoDocumento();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */
    public String irTipoDocuento() throws SQLException {
        list_tdocumento = servicioTipoDocumento.listaTipoDocumento();
        return  "gestionpersonal/tipoDocumento";
    }

    public void abrirNuevoTipoDocumento() {
        this.tipoDocumento = new TipoDocumento();
    }

    public void abrirtipoDocumento () throws SQLException {
        tipoDocumento = servicioTipoDocumento.buscarTipoDocumento(ID_TipoDocumento);
    }

    public void guardarTipoDocumento() throws SQLException {
        if (tipoDocumento.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            tipoDocumento.setEstado("Activo");
            tipoDocumento.setCfecha(timestamp);
            servicioTipoDocumento.guardarTipoDocumento(tipoDocumento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El tipo de documento "+tipoDocumento.getNombre()+" ha sido registrado exitosamente en el sistema!"));
        }else{
            servicioTipoDocumento.ActualizarTipoDocumento(tipoDocumento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El tipo de documento "+tipoDocumento.getNombre()+" ha sido actualizado exitosamente en el sistema!"));
        }
        list_tdocumento = servicioTipoDocumento.listaTipoDocumento();
        PrimeFaces.current().executeScript("PF('tddialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-td");
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        TipoDocumento td = (TipoDocumento) value;
        return  (td.getId()>=filterInt && td.getId()<=filterInt)
                ||td.getNombre().toLowerCase().contains(filterText)
                || td.getDescripcion().toLowerCase().contains(filterText)
                || td.getEstado().toLowerCase().contains(filterText);
    }

    public void cambiaEstado() throws SQLException {
        tipoDocumento = servicioTipoDocumento.buscarTipoDocumento(ID_TipoDocumento);
        System.out.println(tipoDocumento.getEstado());
        switch (tipoDocumento.getEstado()){
            case "Activo":
                tipoDocumento.setEstado("Inactivo");
                break;
            case "Inactivo":
                tipoDocumento.setEstado("Activo");
                break;
        }
        servicioTipoDocumento.CambiarEstado(tipoDocumento);
        list_tdocumento = servicioTipoDocumento.listaTipoDocumento();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado del tipo de documento ha cambiado a "+tipoDocumento.getEstado()+"!"));
        PrimeFaces.current().executeScript("PF('tddialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-td");
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
