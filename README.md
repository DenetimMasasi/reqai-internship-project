# ReqAI – Requirement Analysis Application

ReqAI is a full-stack web application developed during my software development internship at **Etiya**.

The project provides a simple workflow for uploading requirement documents, analyzing their content, storing analysis results, and reviewing previous analyses through a web interface.

## Features

- Upload requirement documents
- Analyze uploaded documents
- Store document and analysis information in PostgreSQL
- View previous analyses through a history page
- Access detailed analysis results
- RESTful backend API
- Interactive API documentation with Swagger/OpenAPI
- Frontend and backend integration

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- Swagger / OpenAPI

### Frontend
- Angular
- TypeScript
- HTML
- CSS

### Database
- PostgreSQL

### Tools
- Git
- GitHub
- Swagger UI
- Maven

## Architecture

The project follows a standard full-stack architecture:

```text
Angular Frontend
       |
       | HTTP / REST
       v
Spring Boot Backend
       |
       | JPA / Hibernate
       v
PostgreSQL Database
```

## Main API Endpoints

Base path:

```text
/api
```

### Health Check

```http
GET /api/health
```

Checks whether the backend service is running.

### Upload Document

```http
POST /api/documents/upload
```

Uploads a requirement document to the application.

### Analyze Document

```http
POST /api/documents/{documentId}/analyze
```

Triggers the analysis process for an uploaded document.

### Analysis History

```http
GET /api/analyses
```

Returns previously created analyses.

### Analysis Details

```http
GET /api/analyses/{analysisId}
```

Returns the details of a specific analysis.

### Mock AI Analysis

```http
POST /api/mock-ai/analyze
```

Provides a mock AI analysis workflow used during development and testing.

## Database

The application uses PostgreSQL to persist uploaded document and analysis information.

The main document entity stores information such as:

- File name
- Original document content
- Analysis status
- Creation date
- Analysis date

## Project Structure

```text
reqai-internship-project/
│
├── backend/
│   └── Spring Boot application
│
├── frontend/
│   └── Angular application
│
└── README.md
```

> The exact directory names may differ depending on the repository structure.

## Running the Project

### Backend

Make sure Java and PostgreSQL are installed and the database configuration is defined in the Spring Boot application settings.

Run the backend using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

After starting the backend, Swagger UI can be used to explore and test the available API endpoints.

### Frontend

Navigate to the Angular frontend directory:

```bash
cd frontend
```

Install the dependencies:

```bash
npm install
```

Start the development server:

```bash
ng serve
```

The frontend can then communicate with the Spring Boot REST API.

## What I Worked On

During this project, I worked on:

- Designing and implementing REST API endpoints with Spring Boot
- Integrating PostgreSQL using Spring Data JPA and Hibernate
- Implementing document upload and analysis workflows
- Developing Angular frontend pages
- Connecting the frontend with backend services
- Creating analysis history and detailed analysis views
- Testing and documenting APIs using Swagger/OpenAPI
- Debugging frontend, backend, and database integration issues

## What I Learned

This project gave me hands-on experience with:

- Full-stack application development
- REST API design
- Frontend–backend integration
- Relational database persistence
- Spring Boot application architecture
- Angular development
- API documentation and testing
- Debugging multi-layer web applications

## Author

**Emre Bıyık**

Information Systems Engineering Student  
Software Development Intern – Etiya

- LinkedIn: [linkedin.com/in/emrebiyik04](https://www.linkedin.com/in/emrebiyik04)
- GitHub: [github.com/DenetimMasasi](https://github.com/DenetimMasasi)
