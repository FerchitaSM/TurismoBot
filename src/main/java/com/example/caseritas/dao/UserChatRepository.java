package com.example.caseritas.dao;

import com.example.caseritas.domain.UserChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserChatRepository extends JpaRepository<UserChatEntity,Integer>{

    @Query( value  =  " SELECT * FROM user_chat where id_user =?1 ORDER BY id_user_chat DESC LIMIT 1 " , nativeQuery  =  true )
    UserChatEntity findLastChatByUserId(Integer userId);

    @Query ( value  =  " SELECT point_conversation FROM user_chat where id_user =?1 ORDER BY id_user_chat DESC LIMIT 1" , nativeQuery  =  true )
    int finUltimatePointConversatonChatByUserId(Integer userId);

    UserChatEntity findByIdUser(int idUser);
}
