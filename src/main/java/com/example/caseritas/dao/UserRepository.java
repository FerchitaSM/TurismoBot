package com.example.caseritas.dao;

import com.example.caseritas.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity,Integer>{

    UserEntity findByIdUserBot(int botUserId);
    UserEntity findByIdUser(int userId);


}