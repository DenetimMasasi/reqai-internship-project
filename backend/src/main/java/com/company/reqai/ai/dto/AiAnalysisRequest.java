package com.company.reqai.ai.dto;

import jakarta.validation.constraints.NotBlank;

public class AiAnalysisRequest {

    @NotBlank(message = "Document content must not be empty.")
    private String content;

    public AiAnalysisRequest() {
    }

    public AiAnalysisRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}