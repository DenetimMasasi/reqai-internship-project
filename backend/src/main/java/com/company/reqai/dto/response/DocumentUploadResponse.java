package com.company.reqai.dto.response;

import com.company.reqai.entity.enums.DocumentStatus;

import java.time.Instant;

public record DocumentUploadResponse(
        Long documentId,
        String fileName,
        DocumentStatus status,
        Instant createdAt
) {
}