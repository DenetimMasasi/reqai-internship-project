package com.company.reqai.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "ReqAI REST API",
        version = "1.0.0",
        description = "REST API for uploading requirement documents and generating structured requirements, tasks and test scenarios.",
        contact = @Contact(
            name = "ReqAI Internship Project"
        )
    )
)
public class OpenApiConfig {
}