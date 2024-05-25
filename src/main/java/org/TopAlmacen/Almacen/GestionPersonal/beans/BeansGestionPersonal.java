package org.TopAlmacen.Almacen.GestionPersonal.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.TopAlmacen.Almacen.GestionJefesArea.dto.JefesArea;
import org.TopAlmacen.Almacen.GestionPersonal.dto.Personas;
import org.TopAlmacen.Almacen.GestionPersonal.servicio.ServicioGestionPersonas;
import org.primefaces.util.LangUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;


@Named
@SessionScoped
public class BeansGestionPersonal implements Serializable {

    @Inject
    private ServicioGestionPersonas servicioGestionPersonas;

    private List<Personas> lstPersona;
    private List<Personas> lstPersonasSeleccionadas;
    private int idSeleccionado;
    private Personas personas;


    public String irGestionPersonal() throws SQLException {
        lstPersona = servicioGestionPersonas.personasList();
        return "gestionpersonal/personas";
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Personas perso = (Personas) value;
        return  (perso.getId()>=filterInt && perso.getId()<=filterInt)
                ||(perso.getPnombre()+ " "+ perso.getSnombre()+ " "+perso.getApaterno() + " "+perso.getAmaterno() ).toLowerCase().contains(filterText);
    }

    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }




    public List<Personas> getLstPersona() {
        return lstPersona;
    }

    public void setLstPersona(List<Personas> lstPersona) {
        this.lstPersona = lstPersona;
    }

    public List<Personas> getLstPersonasSeleccionadas() {
        return lstPersonasSeleccionadas;
    }

    public void setLstPersonasSeleccionadas(List<Personas> lstPersonasSeleccionadas) {
        this.lstPersonasSeleccionadas = lstPersonasSeleccionadas;
    }

    public int getIdSeleccionado() {
        return idSeleccionado;
    }

    public void setIdSeleccionado(int idSeleccionado) {
        this.idSeleccionado = idSeleccionado;
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }
}
