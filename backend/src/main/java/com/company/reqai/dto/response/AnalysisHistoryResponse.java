package com.company.reqai.dto.response;

import java.util.List;

public record AnalysisHistoryResponse(
        List<AnalysisHistoryItemResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}