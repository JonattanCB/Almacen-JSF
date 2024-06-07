package org.TopAlmacen.Almacen.test;


import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.FacesException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Data;
import org.primefaces.event.CaptureEvent;
import org.primefaces.model.file.UploadedFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
@Data
public class serviciotest implements Serializable {
    private String filename;

    private String getRandomImageName() {
        int i = (int) (Math.random() * 10000000);

        return String.valueOf(i);
    }

    public String getFilename() {
        return filename;
    }

    public void oncapture(CaptureEvent captureEvent) {
        System.out.println(captureEvent.getData());
        if (captureEvent.getData() == null){
            System.out.println("Los datos son nulos");
        }

        filename = getRandomImageName();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo"
                + File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".jpeg";

        try {
            byte[] capturedData = captureEvent.getData();

            if (capturedData == null || capturedData.length == 0) {
                System.out.println("Datos no válidos");
                return;
            }

            // Convertir los datos capturados a una cadena Base64
            String encodedData = Base64.getEncoder().encodeToString(capturedData);

            // Escribir la imagen en el sistema de archivos
            byte[] decodedData = Base64.getDecoder().decode(encodedData);
            File file = new File(newFileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(decodedData);
                System.out.println("Imagen guardada correctamente en: " + newFileName);
            } catch (IOException e) {
                System.out.println("Error al escribir la imagen: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



}
