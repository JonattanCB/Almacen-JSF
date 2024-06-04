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

@Named
@SessionScoped
@Data
public class beansGestionRol implements Serializable {

    @Inject
    private ServicioGestionRol servicioGestionRol;

    private List<Rol> lstRol;
    private List<Rol> SeleccionlstRol;
    private Rol rol;
    private int idSeleccionada;

    @PostConstruct
    public void init(){
        try{
            lstRol = servicioGestionRol.listarRol();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

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
            rol.setEstado("1");
            rol.setCfecha(timestamp);
            servicioGestionRol.insertarRol(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Rol Agregado"));
        }else{
            servicioGestionRol.modificarRol(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Rol Editado"));
        }
        lstRol = servicioGestionRol.listarRol();
        PrimeFaces.current().executeScript("PF('roldialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-rol");
    }

    public void AbrirNuevoRol(){
        rol = new Rol();
    }

    public boolean globalFilterFunction(Object value, Object filter) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        System.out.println(filterText);
        Rol rl = (Rol) value;
        return  (rl.getId()>=filterInt && rl.getId()<=filterInt)
                ||rl.getNombre().toLowerCase().contains(filterText)
                || rl.getDescripcion().toLowerCase().contains(filterText);
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
