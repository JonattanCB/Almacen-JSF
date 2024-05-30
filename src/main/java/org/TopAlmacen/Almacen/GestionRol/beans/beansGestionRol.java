package org.TopAlmacen.Almacen.GestionRol.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionRol.dto.Rol;
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
public class beansGestionRol implements Serializable {

    @Inject
    private ServicioGestionRol servicioGestionRol;

    private List<Rol> lstRol;
    private Rol rol;
    private List<Rol> SeleccionlstRol;
    private int idSeleccionada;

    public String irRol() throws SQLException {
        lstRol = servicioGestionRol.listarRol();
        return  "gestionpersonal/roles";
    }

    public void abrirRol() throws SQLException {
        System.out.println(idSeleccionada);
        rol = servicioGestionRol.buscarRol(idSeleccionada);
    }

    public void GuardarRol() throws SQLException {

        if (rol.getId() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            rol.setEstado("1");
            rol.setCfecha(timestamp);
            servicioGestionRol.insertarRol(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Rol Agregado"));
            System.out.println("nuevo");
        }else{
            servicioGestionRol.modificarRol(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Rol Editado"));
            System.out.println("viejo");
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

    public List<Rol> getLstRol() {
        return lstRol;
    }

    public void setLstRol(List<Rol> lstRol) {
        this.lstRol = lstRol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getSeleccionlstRol() {
        return SeleccionlstRol;
    }

    public void setSeleccionlstRol(List<Rol> seleccionlstRol) {
        SeleccionlstRol = seleccionlstRol;
    }

    public int getIdSeleccionada() {
        return idSeleccionada;
    }

    public void setIdSeleccionada(int idSeleccionada) {
        this.idSeleccionada = idSeleccionada;
    }
}
