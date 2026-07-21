package com.company.reqai.dto.response;

import java.time.Instant;

import com.company.reqai.entity.enums.DocumentStatus;

public record AnalysisSummaryResponse(
        Long documentId,
        String fileName,
        DocumentStatus status,
        long requirementCount,
        long taskCount,
        long testScenarioCount,
        Instant analyzedAt
) {
}