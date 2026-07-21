package com.company.reqai.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.reqai.ai.dto.AiAnalysisResponse;
import com.company.reqai.ai.service.RequirementAnalysisService;
import com.company.reqai.dto.response.AnalysisSummaryResponse;
import com.company.reqai.entity.AnalysisDocument;
import com.company.reqai.entity.BusinessRequirement;
import com.company.reqai.entity.enums.DocumentStatus;
import com.company.reqai.exception.AiAnalysisException;
import com.company.reqai.exception.AnalysisConflictException;
import com.company.reqai.exception.DocumentNotFoundException;
import com.company.reqai.mapper.AnalysisResultMapper;
import com.company.reqai.repository.AnalysisDocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisDocumentRepository
            analysisDocumentRepository;

    private final RequirementAnalysisService
            requirementAnalysisService;

    private final AnalysisResultMapper
            analysisResultMapper;

    @Transactional(
        noRollbackFor = AiAnalysisException.class
    )
    public AnalysisSummaryResponse analyzeDocument(
            Long documentId
    ) {
        AnalysisDocument document =
                analysisDocumentRepository
                        .findById(documentId)
                        .orElseThrow(
                                () -> new DocumentNotFoundException(
                                        documentId
                                )
                        );

        validateDocumentStatus(document);

        if (document.getStatus() == DocumentStatus.FAILED) {
            document.clearRequirements();
        }

        document.setStatus(DocumentStatus.PROCESSING);
        document.setAnalyzedAt(null);

        analysisDocumentRepository.flush();

        List<BusinessRequirement> mappedRequirements;

        try {
            AiAnalysisResponse aiResponse =
                    requirementAnalysisService.analyzeText(
                            document.getOriginalContent()
                    );

            mappedRequirements =
                    analysisResultMapper.toEntities(aiResponse);
        } catch (RuntimeException exception) {
            document.clearRequirements();
            document.setStatus(DocumentStatus.FAILED);
            document.setAnalyzedAt(null);

            analysisDocumentRepository.saveAndFlush(document);

            throw new AiAnalysisException(
                    "The document could not be analyzed.",
                    exception
            );
        }

        for (BusinessRequirement requirement
                : mappedRequirements) {
            document.addRequirement(requirement);
        }

        Instant analyzedAt = Instant.now();

        document.setStatus(DocumentStatus.COMPLETED);
        document.setAnalyzedAt(analyzedAt);

        analysisDocumentRepository.saveAndFlush(document);

        long taskCount = mappedRequirements
                .stream()
                .mapToLong(
                        requirement ->
                                requirement.getTasks().size()
                )
                .sum();

        long testScenarioCount = mappedRequirements
                .stream()
                .flatMap(
                        requirement ->
                                requirement.getTasks().stream()
                )
                .mapToLong(
                        task ->
                                task.getTestScenarios().size()
                )
                .sum();

        return new AnalysisSummaryResponse(
                document.getId(),
                document.getFileName(),
                document.getStatus(),
                mappedRequirements.size(),
                taskCount,
                testScenarioCount,
                analyzedAt
        );
    }

    private void validateDocumentStatus(
            AnalysisDocument document
    ) {
        if (document.getStatus()
                == DocumentStatus.PROCESSING) {
            throw new AnalysisConflictException(
                    "The document is already being analyzed."
            );
        }

        if (document.getStatus()
                == DocumentStatus.COMPLETED) {
            throw new AnalysisConflictException(
                    "The document has already been analyzed."
            );
        }
    }
}