package org.TopAlmacen.Almacen.GestionProveedor.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionCategoria.model.Categoria;
import org.TopAlmacen.Almacen.GestionProveedor.model.Proveedor;
import org.TopAlmacen.Almacen.GestionProveedor.servicio.ServicioGestionProveedor;
import org.TopAlmacen.Almacen.GestionRol.model.Rol;
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
public class beansGestionProveedor implements Serializable {

    /*  ================================== Inyecciones  ==================== */
    @Inject
    private ServicioGestionProveedor servicioGestionProveedor;

    @Inject
    private ServicioGestionTipoEmpresa servicioGestionTipoEmpresa;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */
    private List<Proveedor> listProveedor;
    private List<Proveedor> selectedlstProveedores;
    private List<TipoEmpresa> tipoEmpresas;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private int idProveedor;
    private int idTipoEmpresa;
    private boolean activarNuevoEmpresa;
    private boolean verificarVistaData;
    private boolean verificarInputData;
    private Proveedor proveedor;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init() {
        try{
            listProveedor  = servicioGestionProveedor.proveedorList();
            verificarTipoEmpresa();
            verificarInputData = true;
            verificarVistaData= false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */
    public  String irProveedor() throws SQLException {
        listProveedor  = servicioGestionProveedor.proveedorList();
        verificarTipoEmpresa();
        verificarInputData = true;
        verificarVistaData= false;
        return "gestionProducto/Proveedor";
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        Proveedor c = (Proveedor) value;
        return  (c.getIdProveedor()>=filterInt && c.getIdProveedor()<=filterInt)
                ||c.getNombre().toLowerCase().contains(filterText)
                || c.getCorreo().toLowerCase().contains(filterText);
    }

    public void NuevoProveedor() throws SQLException {
        proveedor = new Proveedor();
        tipoEmpresas = servicioGestionTipoEmpresa.listTipoEmpesaActivo();
        verificarTipoEmpresa();
        idTipoEmpresa = 0;
        verificarInputData = true;
        verificarVistaData= false;
    }

    public void guardar() throws SQLException {
        if (proveedor.getIdProveedor() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            proveedor.setTipoEmpresa(servicioGestionTipoEmpresa.buscar(idTipoEmpresa));
            proveedor.setEstado("Activo");
            proveedor.setCfecha(timestamp);
            servicioGestionProveedor.registrar(proveedor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El proveedor "+proveedor.getNombre()+" ha sido registrado exitosamente en el sistema!"));
        }else{
            proveedor.setTipoEmpresa(servicioGestionTipoEmpresa.buscar(idTipoEmpresa));
            servicioGestionProveedor.actualizar(proveedor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El proveedor "+proveedor.getNombre()+" ha sido actualizado exitosamente en el sistema!"));
        }
        verificarTipoEmpresa();
        listProveedor  = servicioGestionProveedor.proveedorList();
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public void Abrirproveedor() throws SQLException {
        proveedor = servicioGestionProveedor.proveedor(idProveedor);
        tipoEmpresas = listTipoEmpresaActualizar(proveedor);
        idTipoEmpresa = proveedor.getTipoEmpresa().getId();
        verificarInputData = true;
        verificarVistaData= false;
    }

    public void AbrirProveedorData() throws SQLException {
        proveedor = servicioGestionProveedor.proveedor(idProveedor);
        verificarInputData = false;
        verificarVistaData= true;
    }

    public  void CambioEstado() throws  SQLException{
        proveedor = servicioGestionProveedor.proveedor(idProveedor);
        switch (proveedor.getEstado()){
            case "Activo":
                proveedor.setEstado("Inactivo");
                break;
            case "Inactivo":
                proveedor.setEstado("Activo");
                break;
        }
        servicioGestionProveedor.CambioEstado(proveedor);
        listProveedor  = servicioGestionProveedor.proveedorList();
        verificarInputData = true;
        verificarVistaData= false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado del proveedor ha cambiado a "+proveedor.getEstado()+"!"));
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    /*  ================================================================= */
    /*  =========================== Extensiones  ========================= */

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    private List<TipoEmpresa> listTipoEmpresaActualizar(Proveedor pro) throws SQLException {
        List<TipoEmpresa> lst = servicioGestionTipoEmpresa.listTipoEmpesaActivo();
        if (pro.getTipoEmpresa().getEstado().equals("Inactivo")){
            lst.add(pro.getTipoEmpresa());
        }
        return lst;
    }

    private void verificarTipoEmpresa() throws  SQLException{
        List<TipoEmpresa> lst = servicioGestionTipoEmpresa.listTipoEmpesaActivo();
        if (lst == null || lst.isEmpty() ){
            activarNuevoEmpresa = true;
        }else {
            activarNuevoEmpresa =false;
        }
    }

    /*  ================================================================== */

}
