package org.TopAlmacen.Almacen.test;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.FacesException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Data;
import org.TopAlmacen.Almacen.GestionUsuario.model.Usuario;
import org.TopAlmacen.Almacen.Utils.conexion;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
@Data
public class serviciotest implements Serializable {
    private Usuario usuario;
    private String urlFoto;
    private String urldata;
    private byte[] foto;
    private int id1;
    conexion con = new conexion();


    private void guardar_fotobd(){
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.test(\n" +
                    "\tfoto)\n" +
                    "\tVALUES ( ?);");
            ps.setBytes(1,foto);
            ps.execute();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private byte[] mandame_foto(int id){
        byte[] fo = null;
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, foto\n" +
                    "\tFROM public.test where id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                fo = rs.getBytes(2);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  fo;
    }



    @PostConstruct
    public void init() {
        usuario = new Usuario();
        urlFoto = "/resources/demo/images/photocam/foto.png";
        urldata = "/resources/demo/images/photocam/foto.png";
    }

    public void guardar(){
        guardar_fotobd();
        eliminarArchivo(urlFoto);

    }


    public void dataImg(){
        foto =mandame_foto(id1);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        // Genera un nombre único para el archivo
        String fileName = "desde_la_bd"+ ".png" ;



        String urlPatch= externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo"
                + File.separator + "images" + File.separator + "photocam" + File.separator + fileName;

        File file = new File(urlPatch);
        file.getParentFile().mkdirs();

        System.out.println("Ruta del archivo: " + urlPatch);

        try {
            if (file.exists()) {
                System.out.println("El archivo ya existe.");
            } else {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(foto);
                    System.out.println("Archivo guardado exitosamente en: " + urlPatch);
                }
            }
            urldata = "/resources/demo/images/photocam/" + fileName;
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
            e.printStackTrace();
        }

    }



    public void oncapture(FileUploadEvent event ) {

        UploadedFile v = event.getFile();
        System.out.println("entra aca");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();


        String originalFileName = v.getFileName();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        // Genera un nombre único para el archivo
        String fileName = UUID.randomUUID().toString() + "." + extension;


        String lastfinalName = urlFoto;

        String urlPatch= externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo"
                + File.separator + "images" + File.separator + "photocam" + File.separator + fileName;

        if (lastfinalName !=null){
            eliminarArchivo(lastfinalName);
        }


        try (InputStream in = v.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStream out = new FileOutputStream( urlPatch)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                baos.write(buffer, 0, bytesRead);
            }
            foto = baos.toByteArray();
            System.out.println(v.getFileName());
            urlFoto = "/resources/demo/images/photocam/" + fileName;
            System.out.println(urlFoto);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    private boolean eliminarArchivo(String fileName) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        String urlPatch = externalContext.getRealPath("") + File.separator + fileName;

        File file = new File(urlPatch);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo eliminado correctamente.");
                return true;
            } else {
                System.out.println("No se pudo eliminar el archivo.");
                return false;
            }
        } else {
            System.out.println("El archivo no existe.");
            return false;
        }
    }



}
