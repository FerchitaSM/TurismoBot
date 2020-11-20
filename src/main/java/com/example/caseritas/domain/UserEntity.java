package com.example.caseritas.domain;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "turismo_bot", catalog = "")
public class UserEntity {
    private int idUser;
    private int idUserBot;

    @Id
    @Column(name = "id_user")
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "id_user_bot")
    public int getIdUserBot() {
        return idUserBot;
    }

    public void setIdUserBot(int idUserBot) {
        this.idUserBot = idUserBot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (idUser != that.idUser) return false;
        if (idUserBot != that.idUserBot) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + idUserBot;
        return result;
    }
}
