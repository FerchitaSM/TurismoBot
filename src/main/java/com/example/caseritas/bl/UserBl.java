package com.example.caseritas.bl;

import com.example.caseritas.dao.UserChatRepository;
import com.example.caseritas.dao.UserRepository;
import com.example.caseritas.domain.UserChatEntity;
import com.example.caseritas.domain.UserEntity;
import com.example.caseritas.dto.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.transaction.Transactional;
import java.sql.Date;

@Service
public class UserBl {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBl.class);

    private UserRepository userRepository;
    private UserChatRepository userChatRepository;

    @Autowired
    public UserBl(UserRepository userRepository, UserChatRepository userChatRepository) {
        this.userRepository = userRepository;
        this.userChatRepository = userChatRepository;
    }

    //funcion que guarda el usuario y el mensaje
    public void saveMessageAndUser(Update update ) {
        int chat_id = Integer.parseInt(update.getMessage().getChatId().toString());
        if(!existingUser(chat_id)){
            registerUser(update);
        } else {
            findByIdUserBot(update.getMessage().getFrom().getId());
        }
        continueWhitUser(update);
    }

    //funcion para ver si existe el usuario con el id del bot
    public boolean existingUser(int chat_id_bot) {
        LOGGER.info("existingUser.........................");
        boolean ret = true;
        UserEntity usersEntity = this.userRepository.findByIdUserBot(chat_id_bot);
        if (usersEntity == null) {
            ret =false;
        }
        return ret;
    }

    //funcion para buscar usuario por el id del bot
    public UserEntity findByIdUserBot(int id) {
        LOGGER.info("findByIdUserBot.........................");
        UserEntity usersEntity = this.userRepository.findByIdUserBot(id);
        if (usersEntity != null) {
            return usersEntity;
        } else {
            LOGGER.info("usersEntity null");
            throw new RuntimeException("Record cannot found for UsersEntity with ID: " + id);
        }

    }

    //funcion para registrar a un nuevo usuario
    public UserEntity registerUser(Update update) {
        User users = update.getMessage().getFrom();

        LOGGER.info("registrerUser.........................");
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUserBot(users.getId());
        userRepository.save(userEntity);

        int chat_id = Integer.parseInt(update.getMessage().getChatId().toString());
        UserEntity usersEntity = findByIdUserBot(chat_id);
        UserChatEntity userChatEntity = new UserChatEntity();
        userChatEntity.setIdUser(usersEntity.getIdUser());
        userChatEntity.setInMessage(update.getMessage().getText());
        userChatEntity.setOutMessage("response");
        userChatEntity.setPointConversation(0);
        userChatRepository.save(userChatEntity);

        return userEntity;
    }


    //funcion para registrar un nuevo chat del usuario previamente crado
    public void continueWhitUser(Update update ) {
        LOGGER.info("continueWithUser.........................");
        int chat_id = Integer.parseInt(update.getMessage().getChatId().toString());
        UserEntity usersEntity = findByIdUserBot(chat_id);
        String response = "Inicio";
        String inmessage = update.getMessage().getText();
        if (inmessage==null)
            inmessage= "foto";

        UserChatEntity userChatEntity = new UserChatEntity();
        userChatEntity.setIdUser(usersEntity.getIdUser());
        userChatEntity.setInMessage(inmessage);
        userChatEntity.setOutMessage(response);
        userChatEntity.setPointConversation(0);
        userChatRepository.save(userChatEntity);
    }


    @Transactional
    public void changeResponseChatMessage(long chat_id, String response) {
        UserEntity usersEntity = userRepository.findByIdUserBot((int) chat_id );
        UserChatEntity userChatEntity = userChatRepository.findLastChatByUserId(usersEntity.getIdUser());
        userChatEntity.setOutMessage(response);
        userChatRepository.save(userChatEntity);
    }

    @Transactional
    public void changePointConversationChatMessage(long chat_id, String point_conversation) {
        UserEntity usersEntity = userRepository.findByIdUserBot((int) chat_id );
        UserChatEntity userChatEntity = userChatRepository.findLastChatByUserId(usersEntity.getIdUser());
        LOGGER.info(point_conversation);
        LOGGER.info(String.valueOf(userChatEntity.getIdUserChat()));
        userChatEntity.setPointConversation(Integer.parseInt(point_conversation));
        userChatRepository.save(userChatEntity);
    }


    //funcion para regresar el punto de la ultima conversacion
    public String lastPointConversation(Update update ) {
        String ret="0";
        int chat_id = Integer.parseInt(update.getMessage().getChatId().toString());
        UserEntity usersEntity = findByIdUserBot(chat_id);
        ret = String.valueOf(userChatRepository.finUltimatePointConversatonChatByUserId(usersEntity.getIdUser()));

        return ret;
    }
}
