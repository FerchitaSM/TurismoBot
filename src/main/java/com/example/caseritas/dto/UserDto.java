package com.example.caseritas.dto;


import java.sql.Date;

public class UserDto {
    private int idUser;
    private int idUserBot;

    public UserDto(int idUser, int idUserBot) {
        this.idUser = idUser;
        this.idUserBot = idUserBot;
    }

    public UserDto(int idUserBot) {
        this.idUserBot = idUserBot;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUserBot() {
        return idUserBot;
    }

    public void setIdUserBot(int idUserBot) {
        this.idUserBot = idUserBot;
    }
}