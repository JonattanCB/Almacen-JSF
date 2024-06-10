package org.TopAlmacen.Almacen.GestionTipoEmpresa.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.model.TipoEmpresa;
import org.TopAlmacen.Almacen.GestionTipoEmpresa.servicio.ServicioGestionTipoEmpresa;
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
public class beansGestionTipoempresa implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioGestionTipoEmpresa servicioGestionTipoEmpresa;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */
    private List<TipoEmpresa> lstTabla;
    private List<TipoEmpresa> lstSeleccionado;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private TipoEmpresa tipoEmpresa;
    private int id_seleccionada;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init(){
        try {
            lstTabla = servicioGestionTipoEmpresa.lstTodo();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */
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

    public  void CambioEstado() throws SQLException{
        tipoEmpresa = servicioGestionTipoEmpresa.buscar(id_seleccionada);
        switch (tipoEmpresa.getEstado()){
            case "Activo":
                tipoEmpresa.setEstado("Inactivo");
                break;
            case "Inactivo":
                tipoEmpresa.setEstado("Activo");
                break;
        }
        servicioGestionTipoEmpresa.CambioEstado(tipoEmpresa);
        lstTabla = servicioGestionTipoEmpresa.lstTodo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado de tipo de empresa ha cambiado a "+tipoEmpresa.getEstado()+"!"));
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public void  guardar() throws SQLException {
        if (tipoEmpresa.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            tipoEmpresa.setEstado("Activo");
            tipoEmpresa.setCfecha(timestamp);
            servicioGestionTipoEmpresa.registrar(tipoEmpresa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El tipo de empresa "+tipoEmpresa.getNombre()+" ha sido registrado exitosamente en el sistema!"));
        }else{
            servicioGestionTipoEmpresa.modificar(tipoEmpresa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El tipo de empresa "+tipoEmpresa.getNombre()+" ha sido actualizado exitosamente en el sistema!"));
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
