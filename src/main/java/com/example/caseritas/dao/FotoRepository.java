package com.example.caseritas.dao;

import com.example.caseritas.domain.FotoEntity;
import com.example.caseritas.domain.PosibleLugarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository  extends JpaRepository<FotoEntity,Integer> {
    List<FotoEntity> findByIdPosibleLugar(int posiblelugar);
}
