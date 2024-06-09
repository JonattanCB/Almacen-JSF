package org.TopAlmacen.Almacen.GestionProveedor.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
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
    private Proveedor proveedor;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init() {
        try{
            listProveedor  = servicioGestionProveedor.proveedorList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */
    public  String irProveedor() throws SQLException {
        listProveedor  = servicioGestionProveedor.proveedorList();
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
        tipoEmpresas = servicioGestionTipoEmpresa.lstTodo();
        idTipoEmpresa = 0;
    }

    public void guardar() throws SQLException {
        if (proveedor.getIdProveedor() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            proveedor.setTipoEmpresa(servicioGestionTipoEmpresa.buscar(idTipoEmpresa));
            proveedor.setEstado("1");
            proveedor.setCfecha(timestamp);
            servicioGestionProveedor.registrar(proveedor);
        }else{
            proveedor.setTipoEmpresa(servicioGestionTipoEmpresa.buscar(idTipoEmpresa));
            servicioGestionProveedor.actualizar(proveedor);
        }
        listProveedor  = servicioGestionProveedor.proveedorList();
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public void Abrirproveedor() throws SQLException {
        proveedor = servicioGestionProveedor.proveedor(idProveedor);
        tipoEmpresas = servicioGestionTipoEmpresa.lstTodo();
        idTipoEmpresa = proveedor.getTipoEmpresa().getId();
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

    /*  ================================================================== */


}
