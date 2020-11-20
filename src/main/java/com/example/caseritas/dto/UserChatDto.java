package com.example.caseritas.dto;


import java.sql.Date;

public class UserChatDto {
    private int idUserChat;
    private int idUser;
    private String inMessage;
    private String outMessage;
    private Integer idContactNumber;
    private int pointConversation;

    public UserChatDto(int idUserChat, int idUser, String inMessage, String outMessage, Integer idContactNumber, int pointConversation) {
        this.idUserChat = idUserChat;
        this.idUser = idUser;
        this.inMessage = inMessage;
        this.outMessage = outMessage;
        this.idContactNumber = idContactNumber;
        this.pointConversation = pointConversation;
    }

    public UserChatDto(int idUser, String inMessage, String outMessage, Integer idContactNumber, int pointConversation) {
        this.idUser = idUser;
        this.inMessage = inMessage;
        this.outMessage = outMessage;
        this.idContactNumber = idContactNumber;
        this.pointConversation = pointConversation;
    }

    public int getIdUserChat() {
        return idUserChat;
    }

    public void setIdUserChat(int idUserChat) {
        this.idUserChat = idUserChat;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getInMessage() {
        return inMessage;
    }

    public void setInMessage(String inMessage) {
        this.inMessage = inMessage;
    }

    public String getOutMessage() {
        return outMessage;
    }

    public void setOutMessage(String outMessage) {
        this.outMessage = outMessage;
    }

    public Integer getIdContactNumber() {
        return idContactNumber;
    }

    public void setIdContactNumber(Integer idContactNumber) {
        this.idContactNumber = idContactNumber;
    }

    public int getPointConversation() {
        return pointConversation;
    }

    public void setPointConversation(int pointConversation) {
        this.pointConversation = pointConversation;
    }
}
