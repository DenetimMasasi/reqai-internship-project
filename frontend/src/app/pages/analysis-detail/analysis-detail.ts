import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectorRef,
  Component,
  OnInit,
  inject
} from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AnalysisDetailResponse } from '../../models/api.models';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-analysis-detail-page',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './analysis-detail.html',
  styleUrl: './analysis-detail.css'
})
export class AnalysisDetailPage implements OnInit {
  private readonly apiService = inject(ApiService);
  private readonly route = inject(ActivatedRoute);
  private readonly changeDetectorRef = inject(ChangeDetectorRef);

  analysis: AnalysisDetailResponse | null = null;
  isLoading = false;
  errorMessage = '';

  ngOnInit(): void {
    const documentId = Number(
      this.route.snapshot.paramMap.get('documentId')
    );

    if (!Number.isInteger(documentId) || documentId <= 0) {
      this.errorMessage = 'Invalid document ID.';
      return;
    }

    this.loadAnalysis(documentId);
  }

  private loadAnalysis(documentId: number): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.apiService
      .getAnalysisDetail(documentId)
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.markForCheck();
        })
      )
      .subscribe({
        next: response => {
          this.analysis = response;
          this.changeDetectorRef.markForCheck();
        },
        error: error => {
          this.errorMessage = this.getErrorMessage(
            error,
            'The analysis could not be loaded.'
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

    return defaultMessage;
  }
}