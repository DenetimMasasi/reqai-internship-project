import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectorRef,
  Component,
  inject
} from '@angular/core';
import { finalize } from 'rxjs';

import {
  AnalysisSummaryResponse,
  DocumentUploadResponse
} from '../../models/api.models';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-upload-page',
  standalone: true,
  templateUrl: './upload.html',
  styleUrl: './upload.css'
})
export class UploadPage {
  private readonly apiService = inject(ApiService);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  selectedFile: File | null = null;
  uploadResponse: DocumentUploadResponse | null = null;
  analysisSummary: AnalysisSummaryResponse | null = null;

  isUploading = false;
  isAnalyzing = false;
  errorMessage = '';

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;

    this.errorMessage = '';
    this.uploadResponse = null;
    this.analysisSummary = null;

    if (!file) {
      this.selectedFile = null;
      return;
    }

    if (!file.name.toLowerCase().endsWith('.txt')) {
      this.selectedFile = null;
      this.errorMessage = 'Please select a TXT file.';
      input.value = '';
      return;
    }

    this.selectedFile = file;
  }

  uploadDocument(): void {
    if (!this.selectedFile || this.isUploading) {
      return;
    }

    this.errorMessage = '';
    this.uploadResponse = null;
    this.analysisSummary = null;
    this.isUploading = true;

    this.apiService
      .uploadDocument(this.selectedFile)
      .pipe(
        finalize(() => {
          this.isUploading = false;
          this.changeDetectorRef.markForCheck();
        })
      )
      .subscribe({
        next: response => {
          this.uploadResponse = response;
          this.changeDetectorRef.markForCheck();
        },
        error: error => {
          this.errorMessage = this.getErrorMessage(
            error,
            'The document could not be uploaded.'
          );

          this.changeDetectorRef.markForCheck();
        }
      });
  }

  analyzeDocument(): void {
    if (!this.uploadResponse || this.isAnalyzing) {
      return;
    }

    this.errorMessage = '';
    this.analysisSummary = null;
    this.isAnalyzing = true;

    this.apiService
      .analyzeDocument(this.uploadResponse.documentId)
      .pipe(
        finalize(() => {
          this.isAnalyzing = false;
          this.changeDetectorRef.markForCheck();
        })
      )
      .subscribe({
        next: response => {
          this.analysisSummary = response;
          this.changeDetectorRef.markForCheck();
        },
        error: error => {
          this.errorMessage = this.getErrorMessage(
            error,
            'The document could not be analyzed.'
          );

          this.changeDetectorRef.markForCheck();
        }
      });
  }

  private getErrorMessage(
    error: HttpErrorResponse,
    defaultMessage: string
  ): string {
    if (
      error.error &&
      typeof error.error === 'object' &&
      typeof error.error.message === 'string'
    ) {
      return error.error.message;
    }

    if (typeof error.error === 'string' && error.error.trim()) {
      return error.error;
    }

    return defaultMessage;
  }
}