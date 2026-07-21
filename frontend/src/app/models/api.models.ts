export type DocumentStatus =
  | 'UPLOADED'
  | 'PROCESSING'
  | 'COMPLETED'
  | 'FAILED';

export type Priority = 'LOW' | 'MEDIUM' | 'HIGH';

export type Complexity = 'LOW' | 'MEDIUM' | 'HIGH';

export type TaskStatus =
  | 'NEW'
  | 'IN_PROGRESS'
  | 'COMPLETED';

export interface DocumentUploadResponse {
  documentId: number;
  fileName: string;
  status: DocumentStatus;
  createdAt: string;
}

export interface AnalysisSummaryResponse {
  documentId: number;
  fileName: string;
  status: DocumentStatus;
  requirementCount: number;
  taskCount: number;
  testScenarioCount: number;
  analyzedAt: string;
}

export interface AnalysisHistoryItem {
  documentId: number;
  fileName: string;
  status: DocumentStatus;
  requirementCount: number;
  createdAt: string;
  analyzedAt: string | null;
}

export interface AnalysisHistoryResponse {
  content: AnalysisHistoryItem[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface TestScenario {
  id: number;
  title: string;
  expectedResult: string;
}

export interface DevelopmentTask {
  id: number;
  title: string;
  description: string;
  status: TaskStatus;
  testScenarios: TestScenario[];
}

export interface BusinessRequirement {
  id: number;
  title: string;
  description: string;
  priority: Priority;
  complexity: Complexity;
  tasks: DevelopmentTask[];
}

export interface AnalysisDetailResponse {
  documentId: number;
  fileName: string;
  status: DocumentStatus;
  createdAt: string;
  analyzedAt: string | null;
  requirements: BusinessRequirement[];
}

export interface ApiErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
}