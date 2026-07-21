package com.company.reqai.dto.response;

import java.util.List;

import com.company.reqai.entity.enums.Complexity;
import com.company.reqai.entity.enums.Priority;

public record RequirementResponse(
        Long id,
        String title,
        String description,
        Priority priority,
        Complexity complexity,
        List<TaskResponse> tasks
) {
}