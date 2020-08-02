package com.example.soloapp.Clases;

import com.google.firebase.firestore.GeoPoint;

public class Fotografo {
    private String nombre;
    private String apellido;
    private String numero;
    private String correo;
    private String id;
    private double latitud;
    private double longitud;
    private boolean activo;

    @Override
    public String toString() {
        return "Fotografo{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", numero='" + numero + '\'' +
                ", correo='" + correo + '\'' +
                ", id='" + id + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", activo=" + activo +
                '}';
    }

    public Fotografo() {
    }

    public Fotografo(String nombre, String apellido, String numero, String correo, String id, double latitud, double longitud, boolean activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.correo = correo;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.activo = activo;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }



    public void setUbicacion (GeoPoint ubicacion){
        this.latitud = ubicacion.getLatitude();
        this.longitud = ubicacion.getLongitude();
    }
}
