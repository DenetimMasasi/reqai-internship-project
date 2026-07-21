package com.company.reqai.dto.response;

import java.util.List;

import com.company.reqai.entity.enums.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        List<TestScenarioResponse> testScenarios
) {
}