package org.TopAlmacen.Almacen.GestionUsuario.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionUsuario.servicio.ServicioPersona;
import org.TopAlmacen.Almacen.GestionUsuario.model.Persona;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;
import org.TopAlmacen.Almacen.GestionRol.servicio.ServicioGestionRol;
import org.TopAlmacen.Almacen.GestionTipoDocumento.model.TipoDocumento;
import org.TopAlmacen.Almacen.GestionTipoDocumento.servicio.ServicioTipoDocumento;
import org.TopAlmacen.Almacen.GestionUsuario.model.Usuario;
import org.TopAlmacen.Almacen.GestionUsuario.servicio.ServicioGestionUsuario;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
@Data
public class BeansGestionUsuario implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioPersona servicioPersona;

    @Inject
    private ServicioGestionUsuario servicioGestionUsuario;

    @Inject
    private ServicioTipoDocumento servicioTipoDocumento;

    @Inject
    private ServicioGestionRol servicioGestionRol;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */

    private List<Usuario> lstTabla;
    private List<Usuario> lstTablaSeleccionada;
    private List<TipoDocumento> lstTipoDocumento;
    private List<Rol> lstRol;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private int selectedTipoDocumento;
    private int selectedRol;
    private int idSeleccionadaUsuario;
    private Persona persona;
    private Usuario usuario;
    private Usuario usuarioIngresado;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    private  void init(){
        try{
            usuarioIngresado = new Usuario();
            lstTabla = servicioGestionUsuario.listaUsuario();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */

    public String login() throws SQLException {
        String contraMD5 = MD5(usuarioIngresado.getContrasenia());
        usuarioIngresado.setContrasenia(contraMD5);
        if (servicioGestionUsuario.VerificiarUsuario(usuarioIngresado)){
            return "gestion/dasboard";
        }else {
            usuarioIngresado = new Usuario();
            return "index";
        }
    }

    public void abrirUsuario() throws SQLException {
        this.lstTipoDocumento = servicioTipoDocumento.listaTipoDocumento();
        this.lstRol = servicioGestionRol.listarRol();
        this.usuario = servicioGestionUsuario.buscarUsuario(idSeleccionadaUsuario);
        this.persona = usuario.getPersonas();
        this.selectedTipoDocumento = persona.getTipoDocumento().getId();
        this.selectedRol = usuario.getRol().getId();
    }

    public void guardarUsuario  () throws SQLException {



        lstTabla = servicioGestionUsuario.listaUsuario();
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
    
    private String MD5(String code){
        String hastext ="";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messagedigest = md.digest(code.getBytes());
            BigInteger no = new BigInteger(1,messagedigest);
            hastext = no.toString(16);
            while (hastext.length() < 32){
                hastext = "0"+hastext;
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return  hastext;
    }
    /*  ================================================================== */
}
