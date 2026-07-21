package com.company.reqai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.reqai.dto.response.AnalysisDetailResponse;
import com.company.reqai.dto.response.AnalysisHistoryResponse;
import com.company.reqai.dto.response.AnalysisSummaryResponse;
import com.company.reqai.entity.enums.DocumentStatus;
import com.company.reqai.service.AnalysisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(
    name = "Analyses",
    description = "Requirement document analysis operations"
)
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/documents/{documentId}/analyze")
    @Operation(
        summary = "Analyze an uploaded document",
        description = "Runs the Mock AI analysis for an uploaded document and stores the generated results."
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

    @GetMapping("/analyses")
    @Operation(
        summary = "List analysis history",
        description = "Returns uploaded documents ordered from newest to oldest with optional status filtering."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Analysis history returned successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid pagination or status parameter"
        )
    })
    public ResponseEntity<AnalysisHistoryResponse>
    getAnalysisHistory(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            DocumentStatus status
    ) {
        AnalysisHistoryResponse response =
                analysisService.getAnalysisHistory(
                        page,
                        size,
                        status
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/analyses/{documentId}")
    @Operation(
        summary = "View complete analysis details",
        description = "Returns the generated requirements, tasks and test scenarios for a completed document analysis."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Analysis details returned successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Document was not found"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Document analysis has not been completed"
        )
    })
    public ResponseEntity<AnalysisDetailResponse>
    getAnalysisDetail(
            @Parameter(
                description = "ID of the analyzed document",
                required = true
            )
            @PathVariable Long documentId
    ) {
        AnalysisDetailResponse response =
                analysisService.getAnalysisDetail(documentId);

        return ResponseEntity.ok(response);
    }
}