# ReqAI Project Analysis

## Project Name

ReqAI – AI-Powered Requirement Decomposition Platform

## Problem Statement

Customer requirement documents are often written as long and unstructured text. These documents may contain multiple business needs, functional requirements, technical expectations and constraints.

Software teams must manually analyze these documents before development begins. They need to identify individual requirements, divide them into development tasks and create suitable test scenarios.

This manual process can be time-consuming and may cause missing, duplicated or misunderstood requirements.

## Project Objective

ReqAI aims to simplify requirement analysis using an AI service or a Mock AI service.

The application allows a user to upload a customer requirement document in TXT format. The backend reads the file content and sends it to the analysis service.

The analysis service generates:

- Business requirements
- Development tasks
- Test scenarios
- Priority levels
- Complexity estimates

The generated results are stored in a relational database and displayed through an Angular web application.

## Main User Workflow

1. The user selects a TXT requirement document.
2. The document is uploaded to the Spring Boot backend.
3. The backend validates and reads the document.
4. The document content is sent to an AI or Mock AI service.
5. The AI service generates structured analysis results.
6. The results are stored in PostgreSQL.
7. The Angular application displays the analysis results.
8. The user can later view previously analyzed documents.

## Project Scope

The first version of ReqAI will include:

- Uploading TXT requirement documents
- Reading and validating uploaded files
- AI or Mock AI integration
- Generating business requirements
- Generating development tasks
- Generating test scenarios
- Assigning priority and complexity values
- Storing generated results in PostgreSQL
- Displaying analysis results in Angular
- Viewing analysis history

## Out of Scope

The first version will not include:

- PDF or Word document upload
- User authentication and authorization
- Team collaboration features
- Editing generated requirements
- Exporting results to external project management tools
- Production payment or subscription features

These features may be considered as future improvements.

## Sample Requirement Document

The Customer Self-Service Portal Improvements document will be used as the initial sample input.

The sample document contains requirements related to:

- User authentication
- Product catalog
- Shopping cart
- Order management
- Payment
- Notifications
- Customer profile

ReqAI will analyze this document. ReqAI will not implement the customer portal itself.

## Technology Stack

### Backend

- Java
- Spring Boot
- Spring Data JPA
- REST API
- Swagger/OpenAPI

### Frontend

- Angular
- SCSS
- Reactive Forms
- Angular Router

### Database

- PostgreSQL

### AI Integration

- Mock AI Service for the first implementation
- Optional real AI integration as a future improvement

### Version Control

- Git
- GitHub
- GitHub Desktop

## Success Criteria

The project will be considered successful when a user can:

1. Upload a valid TXT requirement document.
2. Start the analysis process.
3. Receive structured requirements, tasks and test scenarios.
4. View priority and complexity information.
5. View previously completed analyses.