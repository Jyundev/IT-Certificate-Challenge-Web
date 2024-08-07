package com.web.ddajait.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.ddajait.model.dao.CertificationRegistrationDao;
import com.web.ddajait.model.entity.CertificationRegistrationEntity;
import com.web.ddajait.model.repository.CertificateRegieterRepogitory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificationRegistrationImpl implements CertificationRegistrationDao {

    private final CertificateRegieterRepogitory certificateRegieterRepogitory;

    @Override
    public List<CertificationRegistrationEntity> getAllCerticationResgitration() {
        log.info("[CertificationRegistrationImpl][getAllCerticationResgitration] Starts");
        return certificateRegieterRepogitory.findAll();
    }

    // @Override
    // public Optional<CertificationRegistrationEntity> findByCertificateId(Long
    // certificateId) {
    // return
    // certificateRegieterRepogitory.findByCertificateInfo_certificateId(certificateId);
    // }

}
