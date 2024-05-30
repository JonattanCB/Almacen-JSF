package org.TopAlmacen.Almacen.GestionUsuario.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionPersona.dto.Persona;
import org.TopAlmacen.Almacen.GestionTipoDocumento.dto.TipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.servicio.ServicioTipoDocumento;
import org.TopAlmacen.Almacen.GestionUsuario.dto.Usuario;
import org.TopAlmacen.Almacen.GestionUsuario.servicio.ServicioGestionUsuario;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class BeansGestionUsuario implements Serializable {

    @Inject
    private ServicioGestionUsuario servicioGestionUsuario;

    @Inject
    private ServicioTipoDocumento servicioTipoDocumento;

    private List<Usuario> lstTabla;
    private List<Usuario> lstTablaSeleccionada;
    private Usuario usuario;
    private List<TipoDocumento> lstTipoDocumento;
    private TipoDocumento tipoDocumento;
    private TipoDocumento selectedTipoDocumento;
    private Persona persona;


    public void guardarUsuario(){
        System.out.println(persona.getPnombre());
        System.out.println(selectedTipoDocumento);
    }

    public String irUsuario() throws SQLException {
        lstTabla = servicioGestionUsuario.listaUsuario();
        return "gestionpersonal/usuarios";
    }

    public void abrirNuevoUsuario() throws SQLException {
        lstTipoDocumento = servicioTipoDocumento.listaTipoDocumento();
        this.tipoDocumento = new TipoDocumento();
        this.persona = new Persona();
        this.usuario = new Usuario();
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







    public List<Usuario> getLstTabla() {
        return lstTabla;
    }

    public void setLstTabla(List<Usuario> lstTabla) {
        this.lstTabla = lstTabla;
    }

    public List<Usuario> getLstTablaSeleccionada() {
        return lstTablaSeleccionada;
    }

    public void setLstTablaSeleccionada(List<Usuario> lstTablaSeleccionada) {
        this.lstTablaSeleccionada = lstTablaSeleccionada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<TipoDocumento> getLstTipoDocumento() {
        return lstTipoDocumento;
    }

    public void setLstTipoDocumento(List<TipoDocumento> lstTipoDocumento) {
        this.lstTipoDocumento = lstTipoDocumento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoDocumento getSelectedTipoDocumento() {
        return selectedTipoDocumento;
    }

    public void setSelectedTipoDocumento(TipoDocumento selectedTipoDocumento) {
        this.selectedTipoDocumento = selectedTipoDocumento;
    }
}
