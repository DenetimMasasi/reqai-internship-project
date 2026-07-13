# ReqAI REST API Design

## 1. Overview

ReqAI provides a REST API that allows the Angular frontend to:

- Upload TXT requirement documents
- Start document analysis
- View previous analyses
- View the complete result of a selected analysis

The backend exchanges structured data using JSON.

Uploaded files are sent using `multipart/form-data`.

Base API path:

```text
/api
```

---

## 2. Planned API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| `GET` | `/api/health` | Check whether the backend is running |
| `POST` | `/api/documents/upload` | Upload a TXT requirement document |
| `POST` | `/api/documents/{documentId}/analyze` | Start analysis for an uploaded document |
| `GET` | `/api/analyses` | List previous analyses |
| `GET` | `/api/analyses/{analysisId}` | View one complete analysis |
| `POST` | `/api/mock-ai/analyze` | Directly test the Mock AI service |

---

# 3. Health Check

## Endpoint

```http
GET /api/health
```

## Purpose

Checks whether the Spring Boot backend is running.

## Successful Response

### Status

```text
200 OK
```

### Body

```json
{
  "status": "UP",
  "application": "ReqAI"
}
```

---

# 4. Upload Requirement Document

## Endpoint

```http
POST /api/documents/upload
```

## Content Type

```text
multipart/form-data
```

## Purpose

Uploads a customer requirement document in TXT format.

The backend will:

1. Check that a file was provided.
2. Validate the file extension.
3. Validate that the file is not empty.
4. Read the text content.
5. Store the uploaded document.
6. Set the document status to `UPLOADED`.

## Request

| Field | Type | Required | Description |
|---|---|---:|---|
| `file` | File | Yes | TXT requirement document |

## Successful Response

### Status

```text
201 Created
```

### Body

```json
{
  "documentId": 1,
  "fileName": "customer-self-service-portal.txt",
  "status": "UPLOADED",
  "createdAt": "2026-07-13T17:30:00Z"
}
```

## Possible Errors

### No file provided

```text
400 Bad Request
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "A requirement document must be provided.",
  "path": "/api/documents/upload"
}
```

### Unsupported file type

```text
400 Bad Request
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Only TXT files are supported.",
  "path": "/api/documents/upload"
}
```

### Empty document

```text
400 Bad Request
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "The uploaded document is empty.",
  "path": "/api/documents/upload"
}
```

---

# 5. Start Document Analysis

## Endpoint

```http
POST /api/documents/{documentId}/analyze
```

## Purpose

Starts the analysis process for an uploaded requirement document.

The backend will:

1. Find the uploaded document.
2. Change its status to `PROCESSING`.
3. Send its content to the AI or Mock AI provider.
4. Receive structured requirements, tasks and test scenarios.
5. Store the generated results.
6. Change the status to `COMPLETED`.
7. Return an analysis summary.

## Path Parameter

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `documentId` | Long | Yes | ID of the uploaded document |

## Example Request

```http
POST /api/documents/1/analyze
```

No request body is required.

## Successful Response

### Status

```text
200 OK
```

### Body

```json
{
  "analysisId": 1,
  "documentId": 1,
  "fileName": "customer-self-service-portal.txt",
  "status": "COMPLETED",
  "requirementCount": 7,
  "taskCount": 22,
  "testScenarioCount": 30,
  "analyzedAt": "2026-07-13T17:32:00Z"
}
```

## Possible Errors

### Document not found

```text
404 Not Found
```

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Document with ID 1 was not found.",
  "path": "/api/documents/1/analyze"
}
```

### Document is already being processed

```text
409 Conflict
```

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "The document is already being analyzed.",
  "path": "/api/documents/1/analyze"
}
```

### AI analysis failure

```text
502 Bad Gateway
```

```json
{
  "status": 502,
  "error": "AI Analysis Failed",
  "message": "The document could not be analyzed.",
  "path": "/api/documents/1/analyze"
}
```

---

# 6. List Analysis History

## Endpoint

```http
GET /api/analyses
```

## Purpose

Returns previously uploaded and analyzed documents.

Results should be ordered from newest to oldest.

## Optional Query Parameters

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `page` | Integer | No | Page number, starting from 0 |
| `size` | Integer | No | Number of records per page |
| `status` | String | No | Filter by analysis status |

## Example Request

```http
GET /api/analyses?page=0&size=10
```

## Successful Response

### Status

```text
200 OK
```

### Body

```json
{
  "content": [
    {
      "analysisId": 2,
      "documentId": 2,
      "fileName": "customer-self-service-portal.txt",
      "status": "COMPLETED",
      "requirementCount": 7,
      "createdAt": "2026-07-13T17:30:00Z",
      "analyzedAt": "2026-07-13T17:32:00Z"
    },
    {
      "analysisId": 1,
      "documentId": 1,
      "fileName": "sample-requirements.txt",
      "status": "FAILED",
      "requirementCount": 0,
      "createdAt": "2026-07-12T12:00:00Z",
      "analyzedAt": null
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 2,
  "totalPages": 1
}
```

---

# 7. View Analysis Details

## Endpoint

```http
GET /api/analyses/{analysisId}
```

## Purpose

Returns the complete structured result of a selected analysis.

## Path Parameter

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `analysisId` | Long | Yes | ID of the requested analysis |

## Example Request

```http
GET /api/analyses/1
```

## Successful Response

### Status

```text
200 OK
```

### Body

```json
{
  "analysisId": 1,
  "documentId": 1,
  "fileName": "customer-self-service-portal.txt",
  "status": "COMPLETED",
  "createdAt": "2026-07-13T17:30:00Z",
  "analyzedAt": "2026-07-13T17:32:00Z",
  "requirements": [
    {
      "id": 1,
      "title": "User Authentication",
      "description": "Customers must securely access the portal.",
      "priority": "HIGH",
      "complexity": "MEDIUM",
      "tasks": [
        {
          "id": 1,
          "title": "Develop Login API",
          "description": "Create an endpoint for email and password login.",
          "status": "NEW",
          "testScenarios": [
            {
              "id": 1,
              "title": "Successful login",
              "expectedResult": "The user logs in with valid credentials."
            },
            {
              "id": 2,
              "title": "Invalid password",
              "expectedResult": "The system displays a meaningful error."
            }
          ]
        }
      ]
    }
  ]
}
```

## Possible Error

```text
404 Not Found
```

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Analysis with ID 1 was not found.",
  "path": "/api/analyses/1"
}
```

---

# 8. Mock AI Test Endpoint

## Endpoint

```http
POST /api/mock-ai/analyze
```

## Purpose

Tests the Mock AI module directly without uploading or storing a document.

This endpoint is intended for development and demonstration.

The normal application workflow should use:

```text
POST /api/documents/{documentId}/analyze
```

## Request

```json
{
  "content": "Customers should be able to log in, view products and place orders."
}
```

## Successful Response

```json
{
  "requirements": [
    {
      "title": "User Login",
      "description": "Customers must be able to access the portal securely.",
      "priority": "HIGH",
      "complexity": "MEDIUM",
      "tasks": [
        {
          "title": "Develop Login API",
          "description": "Create an authentication endpoint.",
          "status": "NEW",
          "testScenarios": [
            {
              "title": "Successful login",
              "expectedResult": "The user logs in with valid credentials."
            }
          ]
        }
      ]
    }
  ]
}
```

---

# 9. HTTP Status Codes

| Status Code | Meaning | Example |
|---:|---|---|
| `200` | Request completed successfully | Analysis completed |
| `201` | Resource created successfully | Document uploaded |
| `400` | Request validation failed | Invalid TXT file |
| `404` | Requested resource was not found | Missing document |
| `409` | Request conflicts with current state | Analysis already running |
| `500` | Unexpected backend error | Database or server failure |
| `502` | External AI provider failed | AI analysis error |

---

# 10. Standard Error Response

All backend errors should use a consistent structure.

```json
{
  "timestamp": "2026-07-13T17:35:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Only TXT files are supported.",
  "path": "/api/documents/upload"
}
```

Planned error response fields:

| Field | Type | Description |
|---|---|---|
| `timestamp` | Date and time | Time of the error |
| `status` | Integer | HTTP status code |
| `error` | String | Error category |
| `message` | String | Meaningful user-facing message |
| `path` | String | Requested endpoint |

---

# 11. API Implementation Schedule

## Week 1

- Design API endpoints
- Add Swagger/OpenAPI support
- Display the health endpoint in Swagger

## Week 2

- Implement document upload
- Implement analysis execution
- Integrate Mock AI
- Store results in PostgreSQL
- Implement history and detail endpoints
- Test APIs through Swagger