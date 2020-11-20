package com.example.caseritas.domain;

import javax.persistence.*;

@Entity
@Table(name = "foto", schema = "turismo_bot", catalog = "")
public class FotoEntity {
    private int idFoto;
    private int idPosibleLugar;
    private String image;

    @Id
    @Column(name = "id_foto")
    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    @Basic
    @Column(name = "id_posible_lugar")
    public int getIdPosibleLugar() {
        return idPosibleLugar;
    }

    public void setIdPosibleLugar(int idPosibleLugar) {
        this.idPosibleLugar = idPosibleLugar;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FotoEntity that = (FotoEntity) o;

        if (idFoto != that.idFoto) return false;
        if (idPosibleLugar != that.idPosibleLugar) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFoto;
        result = 31 * result + idPosibleLugar;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
