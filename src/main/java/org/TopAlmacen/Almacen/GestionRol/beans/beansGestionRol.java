package org.TopAlmacen.Almacen.GestionRol.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;
import org.TopAlmacen.Almacen.GestionRol.servicio.ServicioGestionRol;
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
public class beansGestionRol implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioGestionRol servicioGestionRol;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */
    private List<Rol> lstRol;
    private List<Rol> SeleccionlstRol;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private Rol rol;
    private int idSeleccionada;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init(){
        try{
            lstRol = servicioGestionRol.listarRol();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */
    public String irRol() throws SQLException {
        lstRol = servicioGestionRol.listarRol();
        return  "gestionpersonal/roles";
    }

    public void abrirRol() throws SQLException {
        rol = servicioGestionRol.buscarRol(idSeleccionada);
    }

    public void GuardarRol() throws SQLException {
        if (rol.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            rol.setEstado("Activo");
            rol.setCfecha(timestamp);
            servicioGestionRol.insertarRol(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El rol "+rol.getNombre()+" ha sido registrado exitosamente en el sistema!"));
        }else{
            servicioGestionRol.modificarRol(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El rol "+rol.getNombre()+" ha sido actualizado exitosamente en el sistema!"));
        }
        lstRol = servicioGestionRol.listarRol();
        PrimeFaces.current().executeScript("PF('roldialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-rol");
    }

    public void AbrirNuevoRol(){
        rol = new Rol();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        Rol rl = (Rol) value;
        return   (rl.getId()>=filterInt && rl.getId()<=filterInt)
                ||rl.getNombre().toLowerCase().contains(filterText)
                || rl.getDescripcion().toLowerCase().contains(filterText)
                || rl.getEstado().toLowerCase().contains(filterText);
    }

    public void cambiaEstado() throws SQLException {
       rol = servicioGestionRol.buscarRol(idSeleccionada);
       switch (rol.getEstado()){
           case "Activo":
               rol.setEstado("Inactivo");
               break;
           case "Inactivo":
               rol.setEstado("Activo");
               break;
       }
       servicioGestionRol.CambiarEstado(rol);
       lstRol = servicioGestionRol.listarRol();
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado del rol ha cambiado a "+rol.getEstado()+"!"));
       PrimeFaces.current().executeScript("PF('roldialog').hide()");
       PrimeFaces.current().ajax().update("form:messages", "form:dt-rol");
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
