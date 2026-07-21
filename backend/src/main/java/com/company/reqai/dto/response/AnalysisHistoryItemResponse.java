package com.company.reqai.dto.response;

import java.time.Instant;

import com.company.reqai.entity.enums.DocumentStatus;

public record AnalysisHistoryItemResponse(
        Long documentId,
        String fileName,
        DocumentStatus status,
        long requirementCount,
        Instant createdAt,
        Instant analyzedAt
) {
}