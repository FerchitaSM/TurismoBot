package com.example.caseritas.bot;

import com.example.caseritas.bl.BotBl;
import com.example.caseritas.bl.PosibleLugarBl;
import com.example.caseritas.bl.UserBl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BotMain extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(BotMain.class);


    BotBl botBl;
    UserBl userBl;
    PosibleLugarBl posibleLugarBl;

    private static String universal_point="0";
    private static String mensaje=" hola";

    @Autowired
    public BotMain(BotBl botBl, UserBl userBl, PosibleLugarBl posibleLugarBl) {
        this.botBl = botBl;
        this.userBl = userBl;
        this.posibleLugarBl = posibleLugarBl;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();
        userBl.saveMessageAndUser(update);
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(mensaje);
        getMessage(update);
        userBl.changePointConversationChatMessage(update.getMessage().getChatId(), universal_point);


        // BOTONES
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        try {
            keyboardMarkup = punto(update, keyboardMarkup);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (keyboardMarkup != null) {
            message.setReplyMarkup(keyboardMarkup);
        } else {
            ReplyKeyboardRemove keyboardMarkupRemove = new ReplyKeyboardRemove();
            message.setReplyMarkup(keyboardMarkupRemove);
        }


        userBl.changeResponseChatMessage(update.getMessage().getChatId(), mensaje);
        userBl.changePointConversationChatMessage(update.getMessage().getChatId(), universal_point);
        message.setText(mensaje);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public ReplyKeyboardMarkup punto(Update update,ReplyKeyboardMarkup keyboardMarkup) throws IOException {
        String conversacion = userBl.lastPointConversation(update);
        switch (conversacion){
            //ESTE ES EL NIVEL BASICO
            case "0":
                keyboardMarkup=inicio(keyboardMarkup);
                mensaje="Elige una opcion";
                break;
            //ESTE ES EL NIVEL DE REGISTRO
            case "1":
                posibleLugarBl.register(update);
                mensaje="Ingresa el nombre del lugar turistico";
                keyboardMarkup=null;
                universal_point="2";
                break;
            case "2":
                posibleLugarBl.nombre(update);
                mensaje="Ingresa una descripcion del lugar";
                keyboardMarkup=null;
                universal_point="3";
                break;
            case "3":
                posibleLugarBl.descripcion(update);
                mensaje="Ingresa un numero (telefono o celular) de referencia";
                keyboardMarkup=null;
                universal_point="4";
                break;
            case "4":
                posibleLugarBl.numero(update);
                mensaje="Ingresa alguna referencia de la ubicacion del lugar ";
                keyboardMarkup=null;
                universal_point="5";
                break;
            case "5":
                posibleLugarBl.description_ubicacion(update);
                mensaje="Compartenos la ubicacion atravez de google maps";
                keyboardMarkup=null;
                universal_point="6";
                break;
            case "6":
                posibleLugarBl.latitud_longitud(update);
                mensaje="Compartenos fotos del lugar";
                keyboardMarkup=posibleLugarBl.sino(keyboardMarkup);
                universal_point="7";
                break;
            case "7":
                boolean termino = posibleLugarBl.Foto(update);
                if(termino)
                {
                    mensaje="Tu lugar ya esta registrado!\n Que deseas hacer ahora";
                    keyboardMarkup = inicio(keyboardMarkup);
                    mostrarContacto(update);
                    universal_point="0";
                }else {
                    mensaje="Tiene mas fotos? Compartenos";
                    keyboardMarkup=posibleLugarBl.sino(keyboardMarkup);
                    universal_point="7";
                }
                break;
            //ESTE ES EL NIVEL DE INFORMACION
            case "20":
                mensaje="Encuentranos en \n";
                universal_point="0";
                break;
        }
        return keyboardMarkup;
    }


    public ReplyKeyboardMarkup inicio(ReplyKeyboardMarkup keyboardMarkup){
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();// Creando una fila de teclado
        row.add("Registrar Lugar");
        keyboard.add(row);  //primera linea
        row = new KeyboardRow();// Creando otra linea
        row.add("Donde los encuentran");
        keyboard.add(row);// Adicionando la segunda linea
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
}


    public void getMessage (Update update){
        String message=update.getMessage().getText();
        if(message==null)
            message="foto"; // todo
        switch (message){
            case "Registrar Lugar":
                universal_point="1";
                break;
            case "Donde los encuentran":
                universal_point="20";
                break;
            default:
                if(acceptedMessage(update)){
                    log.info("EL MENSAJE ES ADMITIDO");
                }else{
                    universal_point="0";
                }
        }
    }

    public boolean acceptedMessage (Update update){
        boolean ret = false;
        if(universal_point=="1" || universal_point=="2" || universal_point=="3" || universal_point=="4"||
           universal_point=="5" || universal_point=="6"  || universal_point=="7" || universal_point=="20" ){
            ret= true;
        }
        return ret;
    }


    public void mostrarContacto (Update update) {
        long id =update.getMessage().getChatId();
        SendPhoto sendPhoto= posibleLugarBl.mostrarPosibleLugar(update);
        sendPhoto.setChatId(id);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "Turismo_Caseritas_bot";
    }

    @Override
    public String getBotToken() {
        return "1425909259:AAGqc5hzLEioux0dyzI-68uR5JggHL5bpzY";
    }
}



// TODO falta la ubicacion latitud longitud