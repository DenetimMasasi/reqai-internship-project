package com.company.reqai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.reqai.entity.BusinessRequirement;

public interface BusinessRequirementRepository
        extends JpaRepository<BusinessRequirement, Long> {

    long countByDocument_Id(Long documentId);
}