package com.example.soloapp.Clases;

import java.io.Serializable;
import java.util.Date;

public class Sesion implements Serializable {
    private String idCliente;
    private String idFotografo;
    private Date fecha;
    private Boolean terminada;
    private String id;
    private String nombreFotografo;

    public int getCantidadFotos() {
        return cantidadFotos;
    }

    public void setCantidadFotos(int cantidadFotos) {
        this.cantidadFotos = cantidadFotos;
    }

    private int cantidadFotos;

    public Sesion(String idCliente, String idFotografo, Date fecha, Boolean terminada, String id, String nombreFotografo, int cantidadFotos) {
        this.idCliente = idCliente;
        this.idFotografo = idFotografo;
        this.fecha = fecha;
        this.terminada = terminada;
        this.id = id;
        this.nombreFotografo = nombreFotografo;
        this.cantidadFotos = cantidadFotos;
    }

    public String getNombreFotografo() {
        return nombreFotografo;
    }

    public void setNombreFotografo(String nombreFotografo) {
        this.nombreFotografo = nombreFotografo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdFotografo() {
        return idFotografo;
    }

    public void setIdFotografo(String idFotografo) {
        this.idFotografo = idFotografo;
    }


    public Sesion() {
    }





    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getTerminada() {
        return terminada;
    }

    public void setTerminada(Boolean terminada) {
        this.terminada = terminada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
