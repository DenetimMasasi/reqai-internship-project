package com.company.reqai.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.company.reqai.ai.dto.AiAnalysisResponse;
import com.company.reqai.ai.dto.RequirementDto;
import com.company.reqai.ai.dto.TaskDto;
import com.company.reqai.ai.dto.TestScenarioDto;
import com.company.reqai.entity.BusinessRequirement;
import com.company.reqai.entity.DevelopmentTask;
import com.company.reqai.entity.TestScenario;
import com.company.reqai.entity.enums.Complexity;
import com.company.reqai.entity.enums.Priority;
import com.company.reqai.entity.enums.TaskStatus;

@Component
public class AnalysisResultMapper {

    public List<BusinessRequirement> toEntities(
            AiAnalysisResponse response
    ) {
        if (response == null
                || response.getRequirements() == null
                || response.getRequirements().isEmpty()) {
            throw new IllegalArgumentException(
                    "The AI service returned no requirements."
            );
        }

        List<BusinessRequirement> requirements = new ArrayList<>();

        for (RequirementDto requirementDto
                : response.getRequirements()) {
            requirements.add(toRequirementEntity(requirementDto));
        }

        return requirements;
    }

    private BusinessRequirement toRequirementEntity(
            RequirementDto requirementDto
    ) {
        if (requirementDto == null) {
            throw new IllegalArgumentException(
                    "The AI response contains an invalid requirement."
            );
        }

        BusinessRequirement requirement =
                new BusinessRequirement();

        requirement.setTitle(
                requireText(
                        requirementDto.getTitle(),
                        "requirement title"
                )
        );

        requirement.setDescription(
                requireText(
                        requirementDto.getDescription(),
                        "requirement description"
                )
        );

        requirement.setPriority(
                parseEnum(
                        Priority.class,
                        requirementDto.getPriority(),
                        "requirement priority"
                )
        );

        requirement.setComplexity(
                parseEnum(
                        Complexity.class,
                        requirementDto.getComplexity(),
                        "requirement complexity"
                )
        );

        List<TaskDto> taskDtos = requirementDto.getTasks();

        if (taskDtos == null || taskDtos.isEmpty()) {
            throw new IllegalArgumentException(
                    "The AI response contains a requirement without tasks."
            );
        }

        for (TaskDto taskDto : taskDtos) {
            requirement.addTask(toTaskEntity(taskDto));
        }

        return requirement;
    }

    private DevelopmentTask toTaskEntity(TaskDto taskDto) {
        if (taskDto == null) {
            throw new IllegalArgumentException(
                    "The AI response contains an invalid task."
            );
        }

        DevelopmentTask task = new DevelopmentTask();

        task.setTitle(
                requireText(
                        taskDto.getTitle(),
                        "task title"
                )
        );

        task.setDescription(
                requireText(
                        taskDto.getDescription(),
                        "task description"
                )
        );

        task.setStatus(
                parseEnum(
                        TaskStatus.class,
                        taskDto.getStatus(),
                        "task status"
                )
        );

        List<TestScenarioDto> testScenarioDtos =
                taskDto.getTestScenarios();

        if (testScenarioDtos == null
                || testScenarioDtos.isEmpty()) {
            throw new IllegalArgumentException(
                    "The AI response contains a task without test scenarios."
            );
        }

        for (TestScenarioDto testScenarioDto
                : testScenarioDtos) {
            task.addTestScenario(
                    toTestScenarioEntity(testScenarioDto)
            );
        }

        return task;
    }

    private TestScenario toTestScenarioEntity(
            TestScenarioDto testScenarioDto
    ) {
        if (testScenarioDto == null) {
            throw new IllegalArgumentException(
                    "The AI response contains an invalid test scenario."
            );
        }

        TestScenario testScenario = new TestScenario();

        testScenario.setTitle(
                requireText(
                        testScenarioDto.getTitle(),
                        "test scenario title"
                )
        );

        testScenario.setExpectedResult(
                requireText(
                        testScenarioDto.getExpectedResult(),
                        "test scenario expected result"
                )
        );

        return testScenario;
    }

    private String requireText(
            String value,
            String fieldName
    ) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "The AI response contains an empty "
                            + fieldName
                            + "."
            );
        }

        return value.trim();
    }

    private <E extends Enum<E>> E parseEnum(
            Class<E> enumType,
            String value,
            String fieldName
    ) {
        String normalizedValue =
                requireText(value, fieldName)
                        .toUpperCase(Locale.ROOT);

        try {
            return Enum.valueOf(
                    enumType,
                    normalizedValue
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "The AI response contains an invalid "
                            + fieldName
                            + ": "
                            + value,
                    exception
            );
        }
    }
}