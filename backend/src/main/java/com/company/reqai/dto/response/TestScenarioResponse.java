package com.company.reqai.dto.response;

public record TestScenarioResponse(
        Long id,
        String title,
        String expectedResult
) {
}