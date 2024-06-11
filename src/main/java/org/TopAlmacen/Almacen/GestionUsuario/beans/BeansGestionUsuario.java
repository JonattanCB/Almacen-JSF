package org.TopAlmacen.Almacen.GestionUsuario.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.LangUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
    private byte[] foto;
    private boolean activarvistaUsuario;
    private boolean activarUsuarioDatos;
    private boolean activeNewButton;
    private boolean activePassword;
    private String urlFoto;
    private String correoAntiguoUsuario;
    private Persona persona;
    private Usuario usuario;
    private Usuario usuarioIngresado;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init(){
        try{
            usuarioIngresado = new Usuario();
            lstTabla = servicioGestionUsuario.listaUsuario();
            urlFoto = "/resources/imagenes/usuario/default.png"+"?ts=" + System.currentTimeMillis();
            activarvistaUsuario = true;
            activarUsuarioDatos=false;
            activePassword = true;
            verificarusoNuevoUsuario();
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
            Usuario user = new Usuario();
            user.setCorreo(usuarioIngresado.getCorreo());
            user.setContrasenia(usuarioIngresado.getContrasenia());
            usuarioIngresado = servicioGestionUsuario.traerLoginUsuario(user);
            if (usuarioIngresado.getEstado().equals("Activo")){
                return "gestion/dasboard";
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"El usuario está desactivado. Por favor, contacte al administrador para más información.",null));
                return "index";
            }
        }else {
            usuarioIngresado = new Usuario();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"Usuario o contraseña incorrectos. Por favor, inténtelo de nuevo.",null));
            PrimeFaces.current().ajax().update("form:messages");
            return "index";
        }
    }

    public String irUsuario() throws SQLException {
        lstTabla = servicioGestionUsuario.listaUsuario();
        activarvistaUsuario = true;
        activarUsuarioDatos=false;
        activePassword = true;
        verificarusoNuevoUsuario();
        return "gestionpersonal/usuarios";
    }

    public void seleccionarFoto(FileUploadEvent event) {
        urlFoto = Seleccion_foto(event.getFile());
    }

    public void abrirNuevoUsuario() throws SQLException {
        this.urlFoto = "/resources/imagenes/usuario/default.png";
        this.foto = null;
        this.selectedTipoDocumento = 0;
        this.selectedRol = 0;
        this.persona = new Persona();
        this.usuario = new Usuario();
        this.lstTipoDocumento = servicioTipoDocumento.listarTDocumentoActivo();
        this.lstRol = servicioGestionRol.listRolActivo();
        activarvistaUsuario = true;
        activarUsuarioDatos=false;
        activePassword = true;
        verificarusoNuevoUsuario();
    }

    public void abrirUsuario() throws SQLException {
        activePassword = false;
        this.usuario = servicioGestionUsuario.buscarUsuario(idSeleccionadaUsuario);
        this.lstTipoDocumento = listTipoDocumentoActualizado(usuario);
        this.lstRol = listRolActualizado(usuario);
        this.persona = usuario.getPersonas();
        this.selectedTipoDocumento = persona.getTipoDocumento().getId();
        this.selectedRol = usuario.getRol().getId();
        this.foto = usuario.getUrlfoto();
        this.correoAntiguoUsuario = usuario.getCorreo();
        descargaImagen(String.valueOf(usuario.getId()));
        activarvistaUsuario =true;
        activarUsuarioDatos= false;
        verificarusoNuevoUsuario();
    }

    public void abrirVistaUsuario() throws SQLException {
        activePassword = false;
        activarvistaUsuario = false;
        activarUsuarioDatos = true;
        this.usuario = servicioGestionUsuario.buscarUsuario(idSeleccionadaUsuario);
        this.foto = usuario.getUrlfoto();
        descargaImagen(String.valueOf(usuario.getId()));
        verificarusoNuevoUsuario();
    }

    public void CambiarEstado() throws SQLException {
        usuario = servicioGestionUsuario.buscarUsuario(idSeleccionadaUsuario);
        switch (usuario.getEstado()){
            case "Activo":
                usuario.setEstado("Inactivo");
                break;
            case "Inactivo":
                usuario.setEstado("Activo");
                break;
        }
        String nombreCompleto = usuario.getPersonas().getPnombre() +" "+usuario.getPersonas().getSnombre() +" "+usuario.getPersonas().getApatero() +" "+usuario.getPersonas().getAmatero();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado del usuario "+nombreCompleto+" ha cambiado a "+usuario.getEstado()+"!"));
        servicioGestionUsuario.CambiarEstado(usuario);
        lstTabla = servicioGestionUsuario.listaUsuario();
        PrimeFaces.current().executeScript("PF('userdialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public void guardarUsuario() throws SQLException {
       if(usuario.getId() == 0){
           if (servicioGestionUsuario.verificarUsuarioRepetido(usuario)){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_WARN,"El correo ya está registrado para otro usuario.",null));
           }else{
               Timestamp timestamp = new Timestamp(System.currentTimeMillis());
               persona.setTipoDocumento(servicioTipoDocumento.buscarTipoDocumento(selectedTipoDocumento));
               persona.setId(servicioPersona.registrarRetornadoID(persona));
               usuario.setPersonas(persona);
               usuario.setRol(servicioGestionRol.buscarRol(selectedRol));
               usuario.setContrasenia(MD5(usuario.getContrasenia()));
               usuario.setEstado("Activo");
               usuario.setCfecha(timestamp);
               usuario.setUrlfoto(foto);
               servicioGestionUsuario.registrarUsuario(usuario);
               eliminarArchivo(urlFoto);
               String nombreCompleto = usuario.getPersonas().getPnombre() + " " + usuario.getPersonas().getSnombre() + " " + usuario.getPersonas().getApatero() + " " + usuario.getPersonas().getAmatero();
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El usuario " + nombreCompleto + " ha sido registrado exitosamente en el sistema!"));
               PrimeFaces.current().executeScript("PF('userdialog').hide()");
           }
       }

       if(usuario.getId() !=0){
           if (usuario.getCorreo().equals(correoAntiguoUsuario)){
               persona.setTipoDocumento(servicioTipoDocumento.buscarTipoDocumento(selectedTipoDocumento));
               servicioPersona.editarPersona(persona);
               usuario.setRol(servicioGestionRol.buscarRol(selectedRol));
               usuario.setUrlfoto(foto);
               servicioGestionUsuario.editarUsuario(usuario);
               eliminarArchivo(urlFoto);
               String nombreCompleto = usuario.getPersonas().getPnombre() + " " + usuario.getPersonas().getSnombre() + " " + usuario.getPersonas().getApatero() + " " + usuario.getPersonas().getAmatero();
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El usuario " + nombreCompleto + " ha sido actualizado exitosamente en el sistema!"));
               PrimeFaces.current().executeScript("PF('userdialog').hide()");
           }else {
               if (servicioGestionUsuario.verificarUsuarioRepetido(usuario)){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( FacesMessage.SEVERITY_WARN,"El correo ya está registrado para otro usuario.",null));
               }else{
                   persona.setTipoDocumento(servicioTipoDocumento.buscarTipoDocumento(selectedTipoDocumento));
                   servicioPersona.editarPersona(persona);
                   usuario.setRol(servicioGestionRol.buscarRol(selectedRol));
                   usuario.setUrlfoto(foto);
                   servicioGestionUsuario.editarUsuario(usuario);
                   eliminarArchivo(urlFoto);
                   String nombreCompleto = usuario.getPersonas().getPnombre() + " " + usuario.getPersonas().getSnombre() + " " + usuario.getPersonas().getApatero() + " " + usuario.getPersonas().getAmatero();
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El usuario " + nombreCompleto + " ha sido actualizado exitosamente en el sistema!"));
                   PrimeFaces.current().executeScript("PF('userdialog').hide()");
               }
           }
       }
        idSeleccionadaUsuario = 0;
        usuario = new Usuario();
        urlFoto = "";
        correoAntiguoUsuario = "";
        foto = null;
        lstTabla = servicioGestionUsuario.listaUsuario();
        verificarusoNuevoUsuario();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-user");
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        Usuario u = (Usuario) value;
        return  (u.getId()>=filterInt && u.getId()<=filterInt) ||
                (u.getPersonas().getPnombre()+" "+u.getPersonas().getSnombre()+" "+u.getPersonas().getApatero()+" "+u.getPersonas().getAmatero()).toLowerCase().contains(filterText)
                || u.getCorreo().toLowerCase().contains(filterText)
                ||(u.getPersonas().getTipoDocumento().getNombre()).toLowerCase().contains(filterText)
                || String.valueOf(u.getPersonas().getNdocumento()).contains(filterText);
    }

    /*  =========================== Extensiones  ========================= */
    /*  ================================================================== */

    private void descargaImagen(String nombre){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String fileName = nombre+".png" ;
        String urlPatch= externalContext.getRealPath("") + File.separator + "resources" + File.separator + "imagenes"
                + File.separator + "usuario" +File.separator+ fileName;
        File file = new File(urlPatch);
        file.getParentFile().mkdirs();

        if (foto == null) {
            urlFoto = "/resources/imagenes/usuario/default.png"+"?ts=" + System.currentTimeMillis();
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(foto);
                urlFoto = "/resources/imagenes/usuario/" + fileName+"?ts=" + System.currentTimeMillis();
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private String Seleccion_foto(UploadedFile uploadedFile) {
        String url = "";
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String originalFileName = uploadedFile.getFileName();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString() + "." + extension;
        String lastfinalName = urlFoto;
        String urlPatch= externalContext.getRealPath("") + File.separator + "resources" + File.separator + "imagenes"
                + File.separator + "usuario" +File.separator+ fileName;
        if (lastfinalName != null && !lastfinalName.equals("/resources/imagenes/usuario/default.png?ts="+ System.currentTimeMillis())){
            eliminarArchivo(urlFoto);
        }
        try (InputStream in = uploadedFile.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStream out = new FileOutputStream(urlPatch)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                baos.write(buffer, 0, bytesRead);
            }
            foto = baos.toByteArray();
            url = "/resources/imagenes/usuario/"+fileName;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return url;
    }

    private boolean eliminarArchivo(String fileName) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String urlPatch = externalContext.getRealPath("") + File.separator + fileName;
        File file = new File(urlPatch);
        if (file.exists()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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

    private List<TipoDocumento> listTipoDocumentoActualizado(Usuario usuario) throws SQLException {
        List<TipoDocumento> lst = servicioTipoDocumento.listarTDocumentoActivo();
        if (usuario.getPersonas().getTipoDocumento().getEstado().equals("Inactivo")){
            lst.add(usuario.getPersonas().getTipoDocumento());
        }
        return  lst;
    }

    private List<Rol> listRolActualizado(Usuario usuario) throws  SQLException{
        List<Rol> lst = servicioGestionRol.listRolActivo();
        if (usuario.getRol().getEstado().equals("Inactivo")){
            lst.add(usuario.getRol());
        }
        return  lst;
    }

    private void verificarusoNuevoUsuario() throws SQLException {
        List<TipoDocumento> listtd = servicioTipoDocumento.listarTDocumentoActivo();
        List<Rol> listrol = servicioGestionRol.listRolActivo();
        if (listtd == null || listtd.isEmpty() || listrol == null || listrol.isEmpty()){
            activeNewButton= true;
        }else{
            activeNewButton= false;
        }
    }

    /*  ================================================================== */
}
