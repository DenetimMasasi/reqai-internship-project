package com.company.reqai.dto.response;

import java.time.Instant;
import java.util.List;

import com.company.reqai.entity.enums.DocumentStatus;

public record AnalysisDetailResponse(
        Long documentId,
        String fileName,
        DocumentStatus status,
        Instant createdAt,
        Instant analyzedAt,
        List<RequirementResponse> requirements
) {
}