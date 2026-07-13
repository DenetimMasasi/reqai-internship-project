# ReqAI UI Wireframes

## 1. Overview

This document contains low-fidelity wireframes for the first version of the ReqAI web application.

The wireframes define the planned page structure, user actions and information hierarchy before Angular development begins.

The first version contains three main pages:

- Document Upload Page
- Analysis Result Page
- Analysis History Page

---

## 2. Shared Application Layout

All pages will use a common navigation layout.

```text
+--------------------------------------------------------------+
| ReqAI                                Upload   Analysis History |
+--------------------------------------------------------------+
|                                                              |
|                       Page Content                           |
|                                                              |
+--------------------------------------------------------------+
```

### Navigation Items

| Item | Destination |
|---|---|
| `ReqAI` logo or title | Upload page |
| `Upload` | Document upload page |
| `Analysis History` | Previous analyses page |

### Responsive Behavior

On smaller screens:

- Navigation items may move into a menu.
- Cards will use the full screen width.
- Tables may become scrollable.
- Buttons will remain large enough for mobile use.

---

# 3. Document Upload Page

## Purpose

The upload page allows the user to select a TXT customer requirement document and start the analysis process.

## Wireframe

```text
+--------------------------------------------------------------+
| ReqAI                                Upload   Analysis History |
+--------------------------------------------------------------+

                     Requirement Analysis

        Upload a customer requirement document and let
        ReqAI generate requirements, tasks and test cases.

        +------------------------------------------------+
        |                                                |
        |              Drag and drop a TXT file           |
        |                       or                       |
        |                [ Choose TXT File ]             |
        |                                                |
        +------------------------------------------------+

        Supported format: TXT
        Maximum file size: To be defined

        Selected file:
        customer-self-service-portal.txt

        [ Upload and Analyze ]

        Status: Ready to upload
```

## Main Components

- Page title
- Short application description
- File selection area
- Selected file information
- Upload and Analyze button
- Validation message area
- Loading indicator
- Upload or analysis status

## Initial State

```text
No file selected

[ Choose TXT File ]

[ Upload and Analyze ]  Disabled
```

## File Selected State

```text
Selected file:
customer-self-service-portal.txt

File type: TXT
File size: 8 KB

[ Remove File ]
[ Upload and Analyze ]
```

## Loading State

```text
Analyzing requirement document...

[ Loading Spinner ]

Please wait while ReqAI generates requirements,
development tasks and test scenarios.
```

## Invalid File State

```text
Error

Only TXT files are supported.
Please select a valid requirement document.

[ Choose Another File ]
```

## Empty File State

```text
Error

The selected document is empty and cannot be analyzed.

[ Choose Another File ]
```

---

# 4. Analysis Result Page

## Purpose

The result page displays the structured analysis generated from the uploaded document.

## Wireframe

```text
+--------------------------------------------------------------+
| ReqAI                                Upload   Analysis History |
+--------------------------------------------------------------+

Analysis Result

File: customer-self-service-portal.txt
Status: COMPLETED
Analyzed at: 13 July 2026, 17:32

+-------------------+-------------------+----------------------+
| Requirements: 7   | Tasks: 22         | Test Scenarios: 30   |
+-------------------+-------------------+----------------------+

+--------------------------------------------------------------+
| User Authentication                         HIGH / MEDIUM     |
|                                                              |
| Customers should securely access the portal using their      |
| email address and password.                                  |
|                                                              |
| Development Tasks                                            |
|                                                              |
|  1. Develop Login API                              NEW        |
|     Create an endpoint for email and password login.          |
|                                                              |
|     Test Scenarios                                            |
|     - Successful login                                       |
|       Expected: User logs in with valid credentials.          |
|                                                              |
|     - Invalid password                                       |
|       Expected: A meaningful error message is displayed.      |
|                                                              |
|  2. Develop Registration API                       NEW        |
|     Create a customer registration endpoint.                  |
+--------------------------------------------------------------+

+--------------------------------------------------------------+
| Product Catalog                              MEDIUM / MEDIUM   |
|                                                              |
| Customers should browse, search and filter products.          |
|                                                              |
| [ View Tasks and Test Scenarios ]                             |
+--------------------------------------------------------------+

[ Back to History ]                    [ Analyze Another File ]
```

## Result Summary

The upper section displays:

- File name
- Analysis status
- Analysis date
- Requirement count
- Task count
- Test scenario count

## Requirement Card

Each requirement card displays:

- Requirement title
- Description
- Priority
- Complexity
- Development tasks
- Test scenarios

## Priority Badges

Planned priority values:

```text
HIGH
MEDIUM
LOW
```

## Complexity Badges

Planned complexity values:

```text
HIGH
MEDIUM
LOW
```

## Expand and Collapse Behavior

Requirements may be displayed as expandable cards.

```text
[+] User Authentication
[-] Product Catalog
[+] Shopping Cart
```

When expanded, the related tasks and test scenarios are displayed.

## Failed Analysis State

```text
Analysis Failed

The document could not be analyzed.

Reason:
The AI service returned an invalid response.

[ Retry Analysis ]
[ Upload Another File ]
```

---

# 5. Analysis History Page

## Purpose

The history page allows the user to view previously uploaded and analyzed documents.

## Desktop Wireframe

```text
+--------------------------------------------------------------+
| ReqAI                                Upload   Analysis History |
+--------------------------------------------------------------+

Analysis History

View previously uploaded and analyzed requirement documents.

Search: [ Search by file name... ]

Status:
[ All ] [ Completed ] [ Processing ] [ Failed ]

+--------------------------------------------------------------+
| File Name               Status      Created At       Actions  |
+--------------------------------------------------------------+
| customer-portal.txt     COMPLETED   13 Jul 2026      [ View ] |
| banking-system.txt      FAILED      12 Jul 2026      [ View ] |
| ecommerce.txt           PROCESSING  11 Jul 2026      [ View ] |
+--------------------------------------------------------------+

Showing 1–3 of 3 analyses

[ Previous ]                                      [ Next ]
```

## Mobile Wireframe

```text
+--------------------------------------+
| ReqAI                           Menu |
+--------------------------------------+

Analysis History

Search:
[ Search by file name... ]

+--------------------------------------+
| customer-portal.txt                  |
| Status: COMPLETED                    |
| Created: 13 Jul 2026                 |
|                             [ View ]  |
+--------------------------------------+

+--------------------------------------+
| banking-system.txt                   |
| Status: FAILED                       |
| Created: 12 Jul 2026                 |
|                             [ View ]  |
+--------------------------------------+
```

## Main Components

- Search field
- Status filter
- Analysis table or cards
- View action
- Pagination controls
- Empty history message
- Loading state
- Error state

## Empty History State

```text
No Analyses Yet

Upload your first customer requirement document
to generate requirements, tasks and test scenarios.

[ Upload Document ]
```

## Loading State

```text
Loading analysis history...

[ Loading Spinner ]
```

## History Error State

```text
Analysis history could not be loaded.

Please try again later.

[ Retry ]
```

---

# 6. Page Navigation Flow

```text
Upload Page
    │
    │ Upload and Analyze
    ▼
Analysis Result Page
    │
    ├── Analyze Another File ───────→ Upload Page
    │
    └── Back to History ────────────→ Analysis History Page

Analysis History Page
    │
    │ View
    ▼
Analysis Result Page
```

---

# 7. Planned Angular Components

The wireframes may later be implemented using the following Angular components:

```text
AppComponent
├── NavbarComponent
├── DocumentUploadPageComponent
│   ├── FileUploadComponent
│   ├── ValidationMessageComponent
│   └── LoadingIndicatorComponent
├── AnalysisResultPageComponent
│   ├── AnalysisSummaryComponent
│   ├── RequirementCardComponent
│   ├── TaskListComponent
│   └── TestScenarioListComponent
└── AnalysisHistoryPageComponent
    ├── AnalysisFilterComponent
    ├── AnalysisTableComponent
    └── PaginationComponent
```

This component list is a preliminary design and may be simplified during Angular implementation.

---

# 8. Form and Validation Rules

## File Input

The file input must:

- Accept TXT files
- Reject unsupported file types
- Reject empty files
- Display the selected file name
- Allow the user to remove the selected file

## Upload Button

The Upload and Analyze button must be disabled when:

- No file is selected
- The selected file is invalid
- An upload or analysis is already running

## Error Messages

Error messages should:

- Explain what went wrong
- Avoid technical stack traces
- Suggest what the user can do next

Example:

```text
Only TXT files are supported. Please choose another file.
```

---

# 9. Visual Style Direction

The first version should use a simple and professional interface.

Planned visual characteristics:

- Clear page headings
- White cards
- Comfortable spacing
- Responsive layout
- Readable typography
- Consistent buttons
- Priority and status badges
- Visible loading and error states

The interface should focus on readability because analysis results may contain many requirements, tasks and test scenarios.

---

# 10. Implementation Schedule

## Week 1

- Prepare low-fidelity wireframes
- Define pages and navigation
- Define loading, empty and error states

## Week 3

- Create Angular pages
- Implement routing
- Connect frontend to backend APIs
- Add responsive styling
- Add form validation
- Implement loading and error states