package org.TopAlmacen.Almacen.GestionCategoria.beans;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionCategoria.Servicio.ServicioGestionCategoria;
import org.TopAlmacen.Almacen.GestionCategoria.model.Categoria;
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
public class beansGestionCategoria implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioGestionCategoria servicioGestionCategoria;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */
    private List<Categoria> lstTabla;
    private List<Categoria> lstSeleccionado;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private Categoria categoria;
    private int id_seleccionada;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init() {
        try{
            lstTabla = servicioGestionCategoria.lstCategoria();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */
    public String irCategoria() throws SQLException {
        lstTabla = servicioGestionCategoria.lstCategoria();
        return "gestionProducto/categoria";
    }

    public void nuevaCategoria(){
        categoria = new Categoria();
    }

    public void cambiarEstado() throws SQLException {
        categoria = servicioGestionCategoria.buscarCategoria(id_seleccionada);
        switch (categoria.getEstado()){
            case "Activo":
                categoria.setEstado("Inactivo");
                break;
            case "Inactivo":
                categoria.setEstado("Activo");
                break;
        }
        servicioGestionCategoria.CambiarEstado(categoria);
        lstTabla = servicioGestionCategoria.lstCategoria();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado de categoria ha cambiado a "+categoria.getEstado()+"!"));
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public void editarCategoria() throws SQLException {
        categoria = servicioGestionCategoria.buscarCategoria(id_seleccionada);
    }

    public void  guardar() throws SQLException {
        if (categoria.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            categoria.setCFecha(timestamp);
            categoria.setEstado("Activo");
            servicioGestionCategoria.registrarCategoria(categoria);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡La categoria "+categoria.getNombre()+" ha sido registrado exitosamente en el sistema!"));
        }else{
            servicioGestionCategoria.actualizar(categoria);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡La categoria "+categoria.getNombre()+" ha sido actualizado exitosamente en el sistema!"));
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
