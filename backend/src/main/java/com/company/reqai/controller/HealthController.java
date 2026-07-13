package com.company.reqai.controller;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(
    name = "Health",
    description = "Backend health check operations"
)
public class HealthController {

    @GetMapping
    @Operation(
        summary = "Check backend status",
        description = "Returns the current status and application name."
    )
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "application", "ReqAI"
        );
    }
}