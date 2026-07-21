package com.company.reqai.ai.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.reqai.ai.dto.AiAnalysisRequest;
import com.company.reqai.ai.dto.AiAnalysisResponse;
import com.company.reqai.ai.service.RequirementAnalysisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mock-ai")
@Tag(
    name = "Mock AI",
    description = "Mock requirement analysis operations"
)
public class MockAiController {

    private final RequirementAnalysisService requirementAnalysisService;

    public MockAiController(
            RequirementAnalysisService requirementAnalysisService
    ) {
        this.requirementAnalysisService = requirementAnalysisService;
    }

    @PostMapping(
        value = "/analyze",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Analyze requirement text with Mock AI",
        description = "Generates mock business requirements, development tasks and test scenarios from customer requirement text."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Requirement text analyzed successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Document content is missing or empty"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Unexpected backend error"
        )
    })
    public ResponseEntity<AiAnalysisResponse> analyze(
            @Valid @RequestBody AiAnalysisRequest request
    ) {
        AiAnalysisResponse response =
                requirementAnalysisService.analyzeText(request.getContent());

        return ResponseEntity.ok(response);
    }
}