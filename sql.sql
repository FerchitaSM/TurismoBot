CREATE TABLE user (
    id_user int NOT NULL AUTO_INCREMENT,  
    id_user_bot int NOT NULL,
    PRIMARY KEY (id_user)
);

CREATE TABLE posible_lugar(
  id_posible_lugar int NOT NULL AUTO_INCREMENT,  
  id_user int NOT NULL,
  nombre varchar(400) DEFAULT NULL,
  descripcion text DEFAULT NULL,
  ubicacion text DEFAULT NULL,
  latitud double DEFAULT NULL,
  longitud double DEFAULT NULL,
  PRIMARY KEY (id_posible_lugar),
  FOREIGN KEY (id_user) REFERENCES user(id_user)
);

CREATE TABLE foto (
  id_foto int NOT NULL AUTO_INCREMENT,  
  id_posible_lugar int NOT NULL,
  image text DEFAULT NULL,
  PRIMARY KEY (id_foto),
  FOREIGN KEY (id_posible_lugar)REFERENCES posible_lugar (id_posible_lugar)
);


CREATE TABLE user_chat (
  id_user_chat int NOT NULL AUTO_INCREMENT,  
  id_user int NOT NULL,
  in_message varchar(400) DEFAULT NULL,
  out_message varchar(400) DEFAULT NULL,
  id_contact_number int(11) DEFAULT NULL,
  point_conversation int(11) NOT NULL,
  PRIMARY KEY (id_user_chat),
  FOREIGN KEY (id_user) REFERENCES user(id_user)
);