import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  AnalysisDetailResponse,
  AnalysisHistoryResponse,
  AnalysisSummaryResponse,
  DocumentStatus,
  DocumentUploadResponse
} from '../models/api.models';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api';

  uploadDocument(file: File): Observable<DocumentUploadResponse> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<DocumentUploadResponse>(
      `${this.baseUrl}/documents/upload`,
      formData
    );
  }

  analyzeDocument(
    documentId: number
  ): Observable<AnalysisSummaryResponse> {
    return this.http.post<AnalysisSummaryResponse>(
      `${this.baseUrl}/documents/${documentId}/analyze`,
      null
    );
  }

  getAnalysisHistory(
    page = 0,
    size = 10,
    status?: DocumentStatus
  ): Observable<AnalysisHistoryResponse> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (status) {
      params = params.set('status', status);
    }

    return this.http.get<AnalysisHistoryResponse>(
      `${this.baseUrl}/analyses`,
      { params }
    );
  }

  getAnalysisDetail(
    documentId: number
  ): Observable<AnalysisDetailResponse> {
    return this.http.get<AnalysisDetailResponse>(
      `${this.baseUrl}/analyses/${documentId}`
    );
  }
}