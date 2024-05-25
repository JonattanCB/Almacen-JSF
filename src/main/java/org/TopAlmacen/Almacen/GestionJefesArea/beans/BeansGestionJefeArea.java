package org.TopAlmacen.Almacen.GestionJefesArea.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionJefesArea.dto.JefesArea;
import org.TopAlmacen.Almacen.GestionJefesArea.servicio.servicioGestionJefeArea;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class BeansGestionJefeArea implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<JefesArea> jefesAreas;
    private List<JefesArea> filtroJefesAreas;


    @Inject
    private servicioGestionJefeArea servicioGestionJefeArea;

    public BeansGestionJefeArea() {
    }

    public String irGestionJefesArea() throws SQLException {
        System.out.println("GestionJefesArea");
        jefesAreas =  servicioGestionJefeArea.listarTodo();
        return "gestionpersonal/JefesArea";
    }



    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);

        JefesArea jefe = (JefesArea) value;
        return  (jefe.getId()>=filterInt && jefe.getId()<=filterInt)
                ||jefe.getNombre().toLowerCase().contains(filterText)
                || jefe.getArea().toLowerCase().contains(filterText)
                || (jefe.getNumero()>=filterInt && jefe.getNumero()<=filterInt);
    }

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    public List<JefesArea> getJefesAreas() {
        return jefesAreas;
    }

    public void setJefesAreas(List<JefesArea> jefesAreas) {
        this.jefesAreas = jefesAreas;
    }

    public List<JefesArea> getFiltroJefesAreas() {
        return filtroJefesAreas;
    }

    public void setFiltroJefesAreas(List<JefesArea> filtroJefesAreas) {
        this.filtroJefesAreas = filtroJefesAreas;
    }
}
