import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectorRef,
  Component,
  OnInit,
  inject
} from '@angular/core';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import {
  AnalysisHistoryResponse
} from '../../models/api.models';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-history-page',
  standalone: true,
  imports: [RouterLink, DatePipe],
  templateUrl: './history.html',
  styleUrl: './history.css'
})
export class HistoryPage implements OnInit {
  private readonly apiService = inject(ApiService);
  private readonly changeDetectorRef =
    inject(ChangeDetectorRef);

  history: AnalysisHistoryResponse | null = null;

  page = 0;
  readonly size = 10;

  isLoading = false;
  errorMessage = '';

  ngOnInit(): void {
    this.loadHistory();
  }

  loadHistory(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.apiService
      .getAnalysisHistory(this.page, this.size)
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.markForCheck();
        })
      )
      .subscribe({
        next: response => {
          this.history = response;
          this.changeDetectorRef.markForCheck();
        },
        error: error => {
          this.errorMessage = this.getErrorMessage(
            error,
            'Analysis history could not be loaded.'
          );

          this.changeDetectorRef.markForCheck();
        }
      });
  }

  previousPage(): void {
    if (this.page <= 0) {
      return;
    }

    this.page--;
    this.loadHistory();
  }

  nextPage(): void {
    if (
      !this.history ||
      this.page >= this.history.totalPages - 1
    ) {
      return;
    }

    this.page++;
    this.loadHistory();
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