package com.company.reqai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.reqai.entity.DevelopmentTask;

public interface DevelopmentTaskRepository
        extends JpaRepository<DevelopmentTask, Long> {

    long countByRequirement_Document_Id(Long documentId);
}