package com.company.reqai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.reqai.dto.response.AnalysisSummaryResponse;
import com.company.reqai.service.AnalysisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Tag(
    name = "Analyses",
    description = "Requirement document analysis operations"
)
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/{documentId}/analyze")
    @Operation(
        summary = "Analyze an uploaded document",
        description = "Runs the Mock AI analysis for an uploaded document and stores the generated requirements, tasks and test scenarios."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Document analyzed successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Document was not found"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Document is already processing or completed"
        ),
        @ApiResponse(
            responseCode = "502",
            description = "AI analysis failed"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Unexpected backend error"
        )
    })
    public ResponseEntity<AnalysisSummaryResponse>
    analyzeDocument(
            @Parameter(
                description = "ID of the uploaded document",
                required = true
            )
            @PathVariable Long documentId
    ) {
        AnalysisSummaryResponse response =
                analysisService.analyzeDocument(documentId);

        return ResponseEntity.ok(response);
    }
}