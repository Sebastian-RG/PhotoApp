package com.example.soloapp.Clases;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

public class Fotografo  implements Serializable {
    private String nombre;
    private String apellido;
    private String numero;
    private String correo;
    private String id;
    private double latitud;
    private double longitud;
    private boolean activo;
    private float rating;
    private int portfolioSize;
    private String descripcion;
    private double precio5;
    private double precio15;
    private double precio25;

    public Fotografo(String nombre, String apellido, String numero, String correo, String id, double latitud, double longitud, boolean activo, float rating, int portfolioSize, String descripcion, double precio5, double precio15, double precio25) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.correo = correo;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.activo = activo;
        this.rating = rating;
        this.portfolioSize = portfolioSize;
        this.descripcion = descripcion;
        this.precio5 = precio5;
        this.precio15 = precio15;
        this.precio25 = precio25;
    }

    public double getPrecio5() {
        return precio5;
    }

    public void setPrecio5(double precio5) {
        this.precio5 = precio5;
    }

    public double getPrecio15() {
        return precio15;
    }

    public void setPrecio15(double precio15) {
        this.precio15 = precio15;
    }

    public double getPrecio25() {
        return precio25;
    }

    public void setPrecio25(double precio25) {
        this.precio25 = precio25;
    }

    public Fotografo() {
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPortfolioSize() {
        return portfolioSize;
    }

    public void setPortfolioSize(int portfolioSize) {
        this.portfolioSize = portfolioSize;
    }
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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
