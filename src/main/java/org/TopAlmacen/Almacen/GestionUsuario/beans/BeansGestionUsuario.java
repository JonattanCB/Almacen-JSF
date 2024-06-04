package org.TopAlmacen.Almacen.GestionUsuario.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionPersona.model.Persona;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;
import org.TopAlmacen.Almacen.GestionRol.servicio.ServicioGestionRol;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.servicio.ServicioTipoDocumento;
import org.TopAlmacen.Almacen.GestionUsuario.model.Usuario;
import org.TopAlmacen.Almacen.GestionUsuario.servicio.ServicioGestionUsuario;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
@Data
public class BeansGestionUsuario implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioGestionUsuario servicioGestionUsuario;

    @Inject
    private ServicioTipoDocumento servicioTipoDocumento;

    @Inject
    private ServicioGestionRol servicioGestionRol;


    private List<Usuario> lstTabla;
    private List<Usuario> lstTablaSeleccionada;
    private List<TipoDocumento> lstTipoDocumento;
    private List<Rol> lstRol;
    private int selectedTipoDocumento;
    private int selectedRol;
    private Persona persona;
    private Usuario usuario;

    public void guardarUsuario(){
        System.out.println("Aca entra");
        System.out.println("ID: Documento "+selectedTipoDocumento);
        System.out.println("ID: Rol" + selectedRol);

        PrimeFaces.current().executeScript("PF('userdialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public String irUsuario() throws SQLException {
        lstTabla = servicioGestionUsuario.listaUsuario();
        return "gestionpersonal/usuarios";
    }

    public void abrirNuevoUsuario() throws SQLException {
        this.selectedTipoDocumento = 0;
        this.selectedRol = 0;
        this.persona = new Persona();
        this.usuario = new Usuario();
        this.lstTipoDocumento = servicioTipoDocumento.listaTipoDocumento();
        this.lstRol = servicioGestionRol.listarRol();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        System.out.println(filterText);
        int filterInt = getInteger(filterText);
        Usuario u = (Usuario) value;
        return  (u.getId()>=filterInt && u.getId()<=filterInt) ||
                (u.getPersonas().getPnombre()+" "+u.getPersonas().getSnombre()+" "+u.getPersonas().getApatero()+" "+u.getPersonas().getAmatero()).toLowerCase().contains(filterText)
                || u.getCorreo().toLowerCase().contains(filterText)
                ||(u.getPersonas().getTipoDocumento().getNombre()).toLowerCase().contains(filterText)
                || String.valueOf(u.getPersonas().getNdocumento()).contains(filterText);
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
