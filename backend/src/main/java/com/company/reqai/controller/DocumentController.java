package com.company.reqai.controller;

import com.company.reqai.dto.response.DocumentUploadResponse;
import com.company.reqai.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Tag(
    name = "Documents",
    description = "Requirement document upload operations"
)
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
        summary = "Upload a requirement document",
        description = "Uploads and stores a requirement document in TXT format."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Document uploaded successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid, empty or unsupported document"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Unexpected backend error"
        )
    })
    public ResponseEntity<DocumentUploadResponse> uploadDocument(
            @RequestPart("file") MultipartFile file
    ) {
        DocumentUploadResponse response =
                documentService.uploadDocument(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}