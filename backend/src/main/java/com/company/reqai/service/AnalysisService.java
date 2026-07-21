package com.company.reqai.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.reqai.ai.dto.AiAnalysisResponse;
import com.company.reqai.ai.service.RequirementAnalysisService;
import com.company.reqai.dto.response.AnalysisDetailResponse;
import com.company.reqai.dto.response.AnalysisHistoryItemResponse;
import com.company.reqai.dto.response.AnalysisHistoryResponse;
import com.company.reqai.dto.response.AnalysisSummaryResponse;
import com.company.reqai.dto.response.RequirementResponse;
import com.company.reqai.dto.response.TaskResponse;
import com.company.reqai.dto.response.TestScenarioResponse;
import com.company.reqai.entity.AnalysisDocument;
import com.company.reqai.entity.BusinessRequirement;
import com.company.reqai.entity.DevelopmentTask;
import com.company.reqai.entity.TestScenario;
import com.company.reqai.entity.enums.DocumentStatus;
import com.company.reqai.exception.AiAnalysisException;
import com.company.reqai.exception.AnalysisConflictException;
import com.company.reqai.exception.DocumentNotFoundException;
import com.company.reqai.exception.InvalidRequestException;
import com.company.reqai.mapper.AnalysisResultMapper;
import com.company.reqai.repository.AnalysisDocumentRepository;
import com.company.reqai.repository.BusinessRequirementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private static final int MAX_PAGE_SIZE = 100;

    private final AnalysisDocumentRepository analysisDocumentRepository;
    private final BusinessRequirementRepository businessRequirementRepository;
    private final RequirementAnalysisService requirementAnalysisService;
    private final AnalysisResultMapper analysisResultMapper;

    @Transactional(noRollbackFor = AiAnalysisException.class)
    public AnalysisSummaryResponse analyzeDocument(Long documentId) {
        AnalysisDocument document = analysisDocumentRepository
                .findById(documentId)
                .orElseThrow(
                        () -> new DocumentNotFoundException(documentId)
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

        for (BusinessRequirement requirement : mappedRequirements) {
            document.addRequirement(requirement);
        }

        Instant analyzedAt = Instant.now();

        document.setStatus(DocumentStatus.COMPLETED);
        document.setAnalyzedAt(analyzedAt);

        analysisDocumentRepository.saveAndFlush(document);

        long taskCount = mappedRequirements
                .stream()
                .mapToLong(
                        requirement -> requirement.getTasks().size()
                )
                .sum();

        long testScenarioCount = mappedRequirements
                .stream()
                .flatMap(
                        requirement -> requirement.getTasks().stream()
                )
                .mapToLong(
                        task -> task.getTestScenarios().size()
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

    @Transactional(readOnly = true)
    public AnalysisHistoryResponse getAnalysisHistory(
            int page,
            int size,
            DocumentStatus status
    ) {
        validatePagination(page, size);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.DESC,
                        "createdAt"
                )
        );

        Page<AnalysisDocument> documentPage;

        if (status == null) {
            documentPage =
                    analysisDocumentRepository.findAll(pageable);
        } else {
            documentPage =
                    analysisDocumentRepository.findByStatus(
                            status,
                            pageable
                    );
        }

        List<AnalysisHistoryItemResponse> content =
                documentPage
                        .getContent()
                        .stream()
                        .map(this::toHistoryItemResponse)
                        .toList();

        return new AnalysisHistoryResponse(
                content,
                documentPage.getNumber(),
                documentPage.getSize(),
                documentPage.getTotalElements(),
                documentPage.getTotalPages()
        );
    }

    @Transactional(readOnly = true)
    public AnalysisDetailResponse getAnalysisDetail(
            Long documentId
    ) {
        AnalysisDocument document = analysisDocumentRepository
                .findById(documentId)
                .orElseThrow(
                        () -> new DocumentNotFoundException(documentId)
                );

        if (document.getStatus() != DocumentStatus.COMPLETED) {
            throw new AnalysisConflictException(
                    "The document analysis has not been completed."
            );
        }

        List<RequirementResponse> requirements =
                document
                        .getRequirements()
                        .stream()
                        .map(this::toRequirementResponse)
                        .toList();

        return new AnalysisDetailResponse(
                document.getId(),
                document.getFileName(),
                document.getStatus(),
                document.getCreatedAt(),
                document.getAnalyzedAt(),
                requirements
        );
    }

    private AnalysisHistoryItemResponse toHistoryItemResponse(
            AnalysisDocument document
    ) {
        long requirementCount =
                businessRequirementRepository
                        .countByDocument_Id(document.getId());

        return new AnalysisHistoryItemResponse(
                document.getId(),
                document.getFileName(),
                document.getStatus(),
                requirementCount,
                document.getCreatedAt(),
                document.getAnalyzedAt()
        );
    }

    private RequirementResponse toRequirementResponse(
            BusinessRequirement requirement
    ) {
        List<TaskResponse> tasks = requirement
                .getTasks()
                .stream()
                .map(this::toTaskResponse)
                .toList();

        return new RequirementResponse(
                requirement.getId(),
                requirement.getTitle(),
                requirement.getDescription(),
                requirement.getPriority(),
                requirement.getComplexity(),
                tasks
        );
    }

    private TaskResponse toTaskResponse(
            DevelopmentTask task
    ) {
        List<TestScenarioResponse> testScenarios = task
                .getTestScenarios()
                .stream()
                .map(this::toTestScenarioResponse)
                .toList();

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                testScenarios
        );
    }

    private TestScenarioResponse toTestScenarioResponse(
            TestScenario testScenario
    ) {
        return new TestScenarioResponse(
                testScenario.getId(),
                testScenario.getTitle(),
                testScenario.getExpectedResult()
        );
    }

    private void validateDocumentStatus(
            AnalysisDocument document
    ) {
        if (document.getStatus() == DocumentStatus.PROCESSING) {
            throw new AnalysisConflictException(
                    "The document is already being analyzed."
            );
        }

        if (document.getStatus() == DocumentStatus.COMPLETED) {
            throw new AnalysisConflictException(
                    "The document has already been analyzed."
            );
        }
    }

    private void validatePagination(
            int page,
            int size
    ) {
        if (page < 0) {
            throw new InvalidRequestException(
                    "Page number cannot be negative."
            );
        }

        if (size < 1) {
            throw new InvalidRequestException(
                    "Page size must be at least 1."
            );
        }

        if (size > MAX_PAGE_SIZE) {
            throw new InvalidRequestException(
                    "Page size cannot be greater than "
                            + MAX_PAGE_SIZE
                            + "."
            );
        }
    }
}