package com.web.ddajait.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.ddajait.model.entity.ChallengePartEntity;

@Repository
public interface ChallengePartRepository extends JpaRepository<ChallengePartEntity, Long> {

    
    List<ChallengePartEntity> findByCertificateInfo_CertificateId(Long challengeId);

    @Query(value = "SELECT * FROM challenge_part WHERE certificateId = ?1", nativeQuery = true)
    List<ChallengePartEntity> findChallengePartsByCertificateId( Long certificateId);

}