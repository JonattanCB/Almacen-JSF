package org.TopAlmacen.Almacen.GestionProducto.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;

@Named
@SessionScoped
@Data
public class beansGestionProducto implements Serializable {

    @PostConstruct
    public void init(){

    }




}
