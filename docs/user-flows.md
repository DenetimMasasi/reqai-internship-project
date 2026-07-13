# ReqAI User Flows

This document describes the main user interactions and system flows of the ReqAI application.

---

## User Flow 1: Upload and Analyze a Requirement Document

### Purpose

This flow describes how a user uploads a customer requirement document and starts the AI analysis process.

### Steps

1. The user opens the ReqAI web application.
2. The user navigates to the document upload page.
3. The user selects a requirement document in TXT format.
4. The frontend validates the selected file.
5. The user clicks the **Upload and Analyze** button.
6. The frontend sends the selected file to the Spring Boot backend.
7. The backend validates the file type and file content.
8. The backend reads the text content of the uploaded document.
9. The document content is sent to the AI or Mock AI service.
10. The analysis service generates:
    - Business requirements
    - Development tasks
    - Test scenarios
    - Priority levels
    - Complexity estimates
11. The backend stores the generated results in PostgreSQL.
12. The frontend redirects the user to the analysis result page.
13. The generated analysis is displayed to the user.

### Visual Flow

```text
Open ReqAI Application
          ↓
Open Upload Page
          ↓
Select TXT File
          ↓
Validate Selected File
          ↓
Click Upload and Analyze
          ↓
Send File to Backend
          ↓
Read File Content
          ↓
Run AI or Mock AI Analysis
          ↓
Store Results in PostgreSQL
          ↓
Display Analysis Result
```

### Successful Result

The uploaded document is analyzed successfully, and the generated requirements, tasks and test scenarios are displayed.

---

## User Flow 2: View Analysis History

### Purpose

This flow describes how a user views documents that were analyzed previously.

### Steps

1. The user opens the ReqAI application.
2. The user navigates to the **Analysis History** page.
3. The frontend requests previous analysis records from the backend.
4. The backend retrieves the analysis records from PostgreSQL.
5. The frontend displays the analysis history.
6. The user selects one of the previous analyses.
7. The frontend requests the selected analysis details.
8. The backend returns the related requirements, tasks and test scenarios.
9. The application displays the selected analysis.

### Visual Flow

```text
Open ReqAI Application
          ↓
Open Analysis History
          ↓
Request Previous Analyses
          ↓
Load Analyses from PostgreSQL
          ↓
Display Analysis List
          ↓
Select an Analysis
          ↓
Load Analysis Details
          ↓
Display Requirements, Tasks and Tests
```

### Successful Result

The user can view the complete details of a previously analyzed document.

---

## User Flow 3: Invalid File Selection

### Purpose

This flow describes what happens when the user selects an unsupported or invalid file.

### Steps

1. The user opens the document upload page.
2. The user selects a file.
3. The frontend checks the file extension.
4. The selected file is not a valid TXT file.
5. The application displays a meaningful validation message.
6. The invalid file is not uploaded.
7. The user selects another file.

### Visual Flow

```text
Select File
     ↓
Validate File
     ↓
Is the File a Valid TXT File?
     ├── Yes → Enable Upload and Analyze
     └── No  → Display Validation Error
```

### Example Error Message

```text
Only TXT files are supported. Please select a valid requirement document.
```

---

## User Flow 4: Empty File Validation

### Purpose

This flow describes what happens when the user selects an empty TXT file.

### Steps

1. The user selects a TXT file.
2. The frontend sends the file to the backend.
3. The backend reads the file content.
4. The backend detects that the document is empty.
5. The analysis process is not started.
6. The backend returns a validation error.
7. The frontend displays the error message.

### Visual Flow

```text
Upload TXT File
       ↓
Read File Content
       ↓
Is the Content Empty?
       ├── No  → Start Analysis
       └── Yes → Return Validation Error
```

### Example Error Message

```text
The uploaded document is empty and cannot be analyzed.
```

---

## User Flow 5: AI Analysis Failure

### Purpose

This flow describes what happens when the AI or Mock AI service cannot complete the analysis.

### Steps

1. The document is uploaded successfully.
2. The backend reads the document content.
3. The backend sends the content to the AI or Mock AI service.
4. The AI service fails or returns an invalid response.
5. The backend marks the analysis status as `FAILED`.
6. The backend returns an error response to the frontend.
7. The frontend displays a meaningful error message.
8. The user may retry the analysis.

### Visual Flow

```text
Upload Document
       ↓
Read Document Content
       ↓
Send Content to AI Service
       ↓
Was the Analysis Successful?
       ├── Yes → Store and Display Results
       └── No  → Mark Analysis as FAILED
                         ↓
                  Display Error Message
                         ↓
                     Allow Retry
```

### Example Error Message

```text
The document could not be analyzed. Please try again.
```

---

## User Flow 6: Backend or Database Failure

### Purpose

This flow describes what happens when the backend or PostgreSQL database is unavailable.

### Steps

1. The frontend sends a request to the backend.
2. The backend encounters a server or database error.
3. The requested operation cannot be completed.
4. The backend returns an appropriate HTTP error response.
5. The frontend displays a general error message.
6. The user may retry the operation later.

### Example Error Message

```text
The operation could not be completed. Please try again later.
```

---

## User Flow Summary

The first version of ReqAI supports the following main flows:

- Uploading and analyzing a TXT requirement document
- Viewing previously completed analyses
- Handling invalid file types
- Handling empty documents
- Handling AI analysis failures
- Handling backend or database failures