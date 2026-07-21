package com.company.reqai.service;

import com.company.reqai.dto.response.DocumentUploadResponse;
import com.company.reqai.entity.AnalysisDocument;
import com.company.reqai.entity.enums.DocumentStatus;
import com.company.reqai.exception.InvalidDocumentException;
import com.company.reqai.repository.AnalysisDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final AnalysisDocumentRepository analysisDocumentRepository;

    @Transactional
    public DocumentUploadResponse uploadDocument(MultipartFile file) {
        validateFile(file);

        String fileName = cleanFileName(file.getOriginalFilename());
        String content = readContent(file);

        if (content.isBlank()) {
            throw new InvalidDocumentException(
                    "The uploaded document is empty."
            );
        }

        AnalysisDocument document = new AnalysisDocument();
        document.setFileName(fileName);
        document.setOriginalContent(content);
        document.setStatus(DocumentStatus.UPLOADED);

        AnalysisDocument savedDocument =
                analysisDocumentRepository.save(document);

        return new DocumentUploadResponse(
                savedDocument.getId(),
                savedDocument.getFileName(),
                savedDocument.getStatus(),
                savedDocument.getCreatedAt()
        );
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidDocumentException(
                    "A requirement document must be provided."
            );
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new InvalidDocumentException(
                    "The uploaded file must have a valid name."
            );
        }

        String lowerCaseFileName =
                originalFileName.toLowerCase(Locale.ROOT);

        if (!lowerCaseFileName.endsWith(".txt")) {
            throw new InvalidDocumentException(
                    "Only TXT files are supported."
            );
        }
    }

    private String cleanFileName(String originalFileName) {
        String cleanedFileName =
                StringUtils.cleanPath(originalFileName);

        if (cleanedFileName.contains("..")) {
            throw new InvalidDocumentException(
                    "The uploaded file name is invalid."
            );
        }

        return cleanedFileName;
    }

    private String readContent(MultipartFile file) {
        try {
            return new String(
                    file.getBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException exception) {
            throw new InvalidDocumentException(
                    "The uploaded document could not be read."
            );
        }
    }
}