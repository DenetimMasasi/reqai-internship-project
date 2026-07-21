package com.company.reqai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.reqai.entity.TestScenario;

public interface TestScenarioRepository
        extends JpaRepository<TestScenario, Long> {

    long countByTask_Requirement_Document_Id(Long documentId);
}