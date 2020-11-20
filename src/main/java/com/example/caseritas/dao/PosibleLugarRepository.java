package com.example.caseritas.dao;

import com.example.caseritas.domain.PosibleLugarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PosibleLugarRepository extends JpaRepository<PosibleLugarEntity,Integer>{
    @Query( value  = " SELECT * FROM posible_lugar WHERE id_user = ?1 ORDER BY id_posible_lugar DESC LIMIT 1 " , nativeQuery  =  true )
    PosibleLugarEntity findLastByIdUser (int idUser);

}
