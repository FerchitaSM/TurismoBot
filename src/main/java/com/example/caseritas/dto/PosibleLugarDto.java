package com.example.caseritas.dto;

public class PosibleLugarDto {
    private int idPosibleLugar;
    private int idUser;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private Double latitud;
    private Double longitud;


    public PosibleLugarDto(int idUser, String nombre, String descripcion, String ubicacion, Double latitud, Double longitud) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdPosibleLugar() {
        return idPosibleLugar;
    }

    public void setIdPosibleLugar(int idPosibleLugar) {
        this.idPosibleLugar = idPosibleLugar;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
