package com.company.reqai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.company.reqai.entity.AnalysisDocument;
import com.company.reqai.entity.enums.DocumentStatus;

public interface AnalysisDocumentRepository
        extends JpaRepository<AnalysisDocument, Long> {

    Page<AnalysisDocument> findByStatus(
            DocumentStatus status,
            Pageable pageable
    );
}