package com.web.ddajait.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ddajait.model.entity.PartQuestionEntity;

public interface PartQuestionRepository extends JpaRepository<PartQuestionEntity, Long> {


    List<PartQuestionEntity> findByChallengePart_PartId(Long partId);

}
