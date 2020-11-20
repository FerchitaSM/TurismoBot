package com.example.caseritas.domain;

import javax.persistence.*;

@Entity
@Table(name = "user_chat", schema = "turismo_bot", catalog = "")
public class UserChatEntity {
    private int idUserChat;
    private int idUser;
    private String inMessage;
    private String outMessage;
    private Integer idContactNumber;
    private int pointConversation;

    @Id
    @Column(name = "id_user_chat")
    public int getIdUserChat() {
        return idUserChat;
    }

    public void setIdUserChat(int idUserChat) {
        this.idUserChat = idUserChat;
    }

    @Basic
    @Column(name = "id_user")
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "in_message")
    public String getInMessage() {
        return inMessage;
    }

    public void setInMessage(String inMessage) {
        this.inMessage = inMessage;
    }

    @Basic
    @Column(name = "out_message")
    public String getOutMessage() {
        return outMessage;
    }

    public void setOutMessage(String outMessage) {
        this.outMessage = outMessage;
    }

    @Basic
    @Column(name = "id_contact_number")
    public Integer getIdContactNumber() {
        return idContactNumber;
    }

    public void setIdContactNumber(Integer idContactNumber) {
        this.idContactNumber = idContactNumber;
    }

    @Basic
    @Column(name = "point_conversation")
    public int getPointConversation() {
        return pointConversation;
    }

    public void setPointConversation(int pointConversation) {
        this.pointConversation = pointConversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserChatEntity that = (UserChatEntity) o;

        if (idUserChat != that.idUserChat) return false;
        if (idUser != that.idUser) return false;
        if (pointConversation != that.pointConversation) return false;
        if (inMessage != null ? !inMessage.equals(that.inMessage) : that.inMessage != null) return false;
        if (outMessage != null ? !outMessage.equals(that.outMessage) : that.outMessage != null) return false;
        if (idContactNumber != null ? !idContactNumber.equals(that.idContactNumber) : that.idContactNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUserChat;
        result = 31 * result + idUser;
        result = 31 * result + (inMessage != null ? inMessage.hashCode() : 0);
        result = 31 * result + (outMessage != null ? outMessage.hashCode() : 0);
        result = 31 * result + (idContactNumber != null ? idContactNumber.hashCode() : 0);
        result = 31 * result + pointConversation;
        return result;
    }
}
