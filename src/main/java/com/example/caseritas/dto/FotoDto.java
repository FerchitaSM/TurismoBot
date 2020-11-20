package com.example.caseritas.dto;

public class FotoDto {
    private int idFoto;
    private int idPosibleLugar;
    private String image;

    public FotoDto(int idPosibleLugar, String image) {
        this.idPosibleLugar = idPosibleLugar;
        this.image = image;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public int getIdPosibleLugar() {
        return idPosibleLugar;
    }

    public void setIdPosibleLugar(int idPosibleLugar) {
        this.idPosibleLugar = idPosibleLugar;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
