package com.example.caseritas.bl;

import com.example.caseritas.dao.FotoRepository;
import com.example.caseritas.dao.PosibleLugarRepository;
import com.example.caseritas.dao.UserChatRepository;
import com.example.caseritas.dao.UserRepository;
import com.example.caseritas.domain.FotoEntity;
import com.example.caseritas.domain.PosibleLugarEntity;
import com.example.caseritas.domain.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class PosibleLugarBl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PosibleLugarBl.class);

    private PosibleLugarRepository posibleLugarRepository;
    private UserRepository userRepository;
    private UserChatRepository userChatRepository;
    private FotoRepository fotoRepository;

    @Autowired
    public PosibleLugarBl(PosibleLugarRepository posibleLugarRepository, UserRepository userRepository, UserChatRepository userChatRepository, FotoRepository fotoRepository) {
        this.posibleLugarRepository = posibleLugarRepository;
        this.userRepository = userRepository;
        this.userChatRepository = userChatRepository;
        this.fotoRepository = fotoRepository;
    }

    public ReplyKeyboardMarkup sino(ReplyKeyboardMarkup keyboardMarkup) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("No");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }






    public SendPhoto mostrarPosibleLugar(Update update) {
        LOGGER.info("mostrarPosibleLugar......................................................");
        PosibleLugarEntity posibleLugarEntity = findPosibleLugar(update);
        FotoEntity fotoEntity = findFoto(posibleLugarEntity);
        String f_id =null;

        if ( fotoEntity != null)
            f_id =fotoEntity.getImage();

        String datos = mostrarDatos(posibleLugarEntity);

        SendPhoto msg = new SendPhoto()
                .setPhoto(f_id)
                .setCaption(datos);
        return msg;
    }

    private FotoEntity findFoto(PosibleLugarEntity posibleLugarEntity) {
        List<FotoEntity> listFotoEntity = new ArrayList<>();
        listFotoEntity = fotoRepository.findByIdPosibleLugar(posibleLugarEntity.getIdPosibleLugar());
        if(listFotoEntity.size()>0)
            return  listFotoEntity.get(0);
        else
            return null;
    }

    public String mostrarDatos(PosibleLugarEntity posibleLugarEntity) {
        String info ="";
        info="Nombre: "+posibleLugarEntity.getNombre()+"\n"
                +"Descripcion: "+posibleLugarEntity.getDescripcion()+"\n"
                +"Ubicacion: "+posibleLugarEntity.getUbicacion()+"\n"
                +"Numero: "+posibleLugarEntity.getNumero().toString()+"\n";
        return info;

    }


    public boolean Foto(Update update) {
        boolean termino = true;
        if(update.getMessage().getPhoto() !=null) { // llego una foto
            Image(update);
            termino = false;
        }
        return termino;
    }


    //funcion para cambiar la imagen
    @Transactional
    public void Image(Update update) {
        List<PhotoSize> photos = update.getMessage().getPhoto();
        String img =photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getFileId();


        PosibleLugarEntity posibleLugarEntity = findPosibleLugar(update);
        FotoEntity fotoEntity = new FotoEntity();
        fotoEntity.setIdPosibleLugar(posibleLugarEntity.getIdPosibleLugar());
        fotoEntity.setImage(img);
        fotoRepository.save(fotoEntity);

    }

    @Transactional
    public void latitud_longitud(Update update) {
            Double latitude = Double.valueOf(update.getMessage().getLocation().getLatitude());
            Double longitude = Double.valueOf(update.getMessage().getLocation().getLongitude());
            PosibleLugarEntity posibleLugarEntity = findPosibleLugar(update);
            posibleLugarEntity.setLatitud(latitude);
            posibleLugarEntity.setLongitud(longitude);
            posibleLugarRepository.save(posibleLugarEntity);


    }

    @Transactional
    public void description_ubicacion(Update update) {
        String name = update.getMessage().getText();
        PosibleLugarEntity posibleLugarEntity = findPosibleLugar(update);
        LOGGER.info("name "+name);
        posibleLugarEntity.setUbicacion(name);
        posibleLugarRepository.save(posibleLugarEntity);
    }

    @Transactional
    public void numero(Update update) {
        String name = update.getMessage().getText();
        PosibleLugarEntity posibleLugarEntity = findPosibleLugar(update);
        posibleLugarEntity.setNumero(Integer.valueOf(name));
        posibleLugarRepository.save(posibleLugarEntity);
    }

    @Transactional
    public void descripcion(Update update) {
        String name = update.getMessage().getText();
        PosibleLugarEntity posibleLugarEntity = findPosibleLugar(update);
        posibleLugarEntity.setDescripcion(name);
        posibleLugarRepository.save(posibleLugarEntity);
    }


    //funcion para cambiar el nombre
    @Transactional
    public void nombre(Update update) {
        String name = update.getMessage().getText();
        PosibleLugarEntity posibleLugarEntity = findPosibleLugar(update);
        posibleLugarEntity.setNombre(name);
        posibleLugarRepository.save(posibleLugarEntity);
    }

    // funcion para crear un posible lugar
    public void register(Update update) {
        int idUserBot= Integer.parseInt(update.getMessage().getChatId().toString());
        int idUser = userRepository.findByIdUserBot(idUserBot).getIdUser();

        PosibleLugarEntity posibleLugarEntity = new PosibleLugarEntity();
        posibleLugarEntity.setIdUser(idUser);
        posibleLugarRepository.save(posibleLugarEntity);
    }


    private PosibleLugarEntity findPosibleLugar(Update update) {
        int chat_id = Integer.parseInt(update.getMessage().getChatId().toString());
        UserEntity usersEntity = userRepository.findByIdUserBot(chat_id);
        PosibleLugarEntity ultimoPosibleLugarEntity = posibleLugarRepository.findLastByIdUser(usersEntity.getIdUser());
        return ultimoPosibleLugarEntity;
    }
}

















