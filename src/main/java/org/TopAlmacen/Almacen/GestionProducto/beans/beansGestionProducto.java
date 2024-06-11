package org.TopAlmacen.Almacen.GestionProducto.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionCategoria.Servicio.ServicioGestionCategoria;
import org.TopAlmacen.Almacen.GestionCategoria.model.Categoria;
import org.TopAlmacen.Almacen.GestionProducto.model.Producto;
import org.TopAlmacen.Almacen.GestionProducto.servicio.ServicioGestionProducto;
import org.TopAlmacen.Almacen.GestionProveedor.model.Proveedor;
import org.TopAlmacen.Almacen.GestionProveedor.servicio.ServicioGestionProveedor;
import org.TopAlmacen.Almacen.GestionStock.model.Stock;
import org.TopAlmacen.Almacen.GestionStock.servicio.ServicioGestionStock;
import org.TopAlmacen.Almacen.GestionTipoUnidad.model.TipoUnidad;
import org.TopAlmacen.Almacen.GestionTipoUnidad.servicio.ServicioGestionTipoUnidad;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.LangUtils;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Named
@SessionScoped
@Data
public class beansGestionProducto implements Serializable {

    /*  ================================== Inyecciones  ==================== */

    @Inject
    private ServicioGestionProducto servicioGestionProducto;

    @Inject
    private ServicioGestionStock servicioGestionStock;

    @Inject
    private ServicioGestionTipoUnidad servicioGestionTipoUnidad;

    @Inject
    private ServicioGestionCategoria servicioGestionCategoria;

    @Inject
    private ServicioGestionProveedor servicioGestionProveedor;

    /*  =================================================================== */
    /*  ================================== Listas  ======================== */
    private List<Producto> productoList;
    private List<Producto> selectproductoList;
    private List<TipoUnidad> unidadList;
    private List<Categoria> categoriaList;
    private List<Proveedor> proveedorList;

    /*  ================================================================== */
    /*  ================================== Variables  ==================== */
    private Producto producto;
    private int idProducto;
    private int idSeleccionCategoria;
    private int idSeleccionTunidad;
    private int idSeleccionProveedor;
    private boolean VerificarNewProducto;
    private boolean verificarVistaData;
    private boolean verificarInputData;
    private String urlFoto;
    private byte[] foto;

    /*  ====================================================================== */
    /*  ================================== Inicializador  ==================== */
    @PostConstruct
    public void init(){
        try{
            productoList = servicioGestionProducto.listProductos();
            urlFoto = "/resources/imagenes/producto/default.png"+"?ts=" + System.currentTimeMillis();
            verificiarNuevoProducto();
            verificarInputData = true;
            verificarVistaData= false;
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    /*  ================================================================= */
    /*  ================================== Metodos  ==================== */

    public String irProducto() throws SQLException {
        productoList = servicioGestionProducto.listProductos();
        verificarInputData = true;
        verificarVistaData= false;
        verificiarNuevoProducto();
        return "gestionProducto/Producto";
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);
        Producto c = (Producto) value;
        return  (c.getIdProducto()>=filterInt && c.getIdProducto()<=filterInt)
                ||c.getNombre().toLowerCase().contains(filterText)
                || c.getCategoria().getNombre().toLowerCase().contains(filterText)
                || c.getProveedor().getNombre().toLowerCase().contains(filterText);

    }

    public void irNuevoProducto() throws SQLException {
        urlFoto = "/resources/imagenes/producto/default.png"+"?ts=" + System.currentTimeMillis();
        foto = null;
        producto = new Producto();
        unidadList = servicioGestionTipoUnidad.listTipoUnidadActivo();
        categoriaList = servicioGestionCategoria.listCategoriaActivo();
        proveedorList= servicioGestionProveedor.lstProveedorActivo();
        idSeleccionCategoria=0;
        idSeleccionTunidad=0;
        idSeleccionProveedor=0;
        verificarInputData = true;
        verificarVistaData= false;
        verificiarNuevoProducto();
    }

    public void irActualizarProducto() throws SQLException {
        producto = servicioGestionProducto.traerProducto(idProducto);
        unidadList = servicioGestionTipoUnidad.lstTipoUnidad();
        categoriaList = servicioGestionCategoria.lstCategoria();
        proveedorList= servicioGestionProveedor.proveedorList();
        idSeleccionCategoria=producto.getCategoria().getId();
        idSeleccionTunidad=producto.getTipoUnidad().getId();
        idSeleccionProveedor=producto.getProveedor().getIdProveedor();
        foto = producto.getFoto();
        descargaImagen(String.valueOf(producto.getIdProducto()));
        verificarInputData = true;
        verificarVistaData= false;
        verificiarNuevoProducto();
    }

    public void seleccionarFoto(FileUploadEvent event){
        urlFoto = Seleccion_foto(event.getFile());
    }

    public void guardar() throws SQLException {
        if(producto.getIdProducto() == 0){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            producto.setProveedor(servicioGestionProveedor.proveedor(idSeleccionProveedor));
            producto.setCategoria(servicioGestionCategoria.buscarCategoria(idSeleccionCategoria));
            producto.setTipoUnidad(servicioGestionTipoUnidad.buscar(idSeleccionTunidad));
            producto.setEstado("Activo");
            producto.setCfecha(timestamp);
            producto.setFoto(foto);
            producto.setIdProducto(servicioGestionProducto.registraProductoRetornaID(producto));
            servicioGestionStock.RegistrarStock(new Stock(0,producto,0));
        }else{
            producto.setProveedor(servicioGestionProveedor.proveedor(idSeleccionProveedor));
            producto.setCategoria(servicioGestionCategoria.buscarCategoria(idSeleccionCategoria));
            producto.setTipoUnidad(servicioGestionTipoUnidad.buscar(idSeleccionTunidad));
            producto.setFoto(foto);
            servicioGestionProducto.ActualizarProducto(producto);
            eliminarArchivo(urlFoto);
        }
        idProducto = 0 ;
        urlFoto="";
        foto = null;
        productoList = servicioGestionProducto.listProductos();
        verificiarNuevoProducto();
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public void CambiarEstado() throws SQLException {
        producto = servicioGestionProducto.traerProducto(idProducto);
        switch (producto.getEstado()){
            case "Activo":
                producto.setEstado("Inactivo");
                break;
            case "Inactivo":
                producto.setEstado("Activo");
                break;
        }
        servicioGestionProducto.CambioEstado(producto);
        productoList = servicioGestionProducto.listProductos();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡El estado del producto ha cambiado a "+producto.getEstado()+"!"));
        PrimeFaces.current().executeScript("PF('dialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt");
    }

    public void irDatoProducto() throws SQLException {
        verificarInputData = false;
        verificarVistaData= true;
        producto = servicioGestionProducto.traerProducto(idProducto);
        foto = producto.getFoto();
        descargaImagen(String.valueOf(producto.getIdProducto()));
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

    private void descargaImagen(String nombre){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String fileName = nombre+".png" ;
        String urlPatch= externalContext.getRealPath("") + File.separator + "resources" + File.separator + "imagenes"
                + File.separator + "producto" +File.separator+ fileName;
        File file = new File(urlPatch);
        file.getParentFile().mkdirs();

        if (foto == null) {
            urlFoto = "/resources/imagenes/producto/default.png"+"?ts=" + System.currentTimeMillis();
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(foto);
            urlFoto = "/resources/imagenes/producto/" + fileName+"?ts=" + System.currentTimeMillis();
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
                + File.separator + "producto" +File.separator+ fileName;
        if (lastfinalName != null && !lastfinalName.equals("/resources/imagenes/producto/default.png?ts="+ System.currentTimeMillis())){
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
            url = "/resources/imagenes/producto/"+fileName;
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

    private void verificiarNuevoProducto() throws SQLException {
        List<TipoUnidad> unidad = servicioGestionTipoUnidad.listTipoUnidadActivo();
        List<Categoria> categoria = servicioGestionCategoria.listCategoriaActivo();
        List<Proveedor> proveedor = servicioGestionProveedor.proveedorList();
        if (unidad == null || unidad.isEmpty() || categoria == null || categoria.isEmpty() || proveedor == null || proveedor.isEmpty()){
            VerificarNewProducto = true;
        }else {
            VerificarNewProducto = false;
        }
    }

    /*  ================================================================== */
}
