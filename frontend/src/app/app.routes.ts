import { Routes } from '@angular/router';

import { AnalysisDetailPage } from './pages/analysis-detail/analysis-detail';
import { UploadPage } from './pages/upload/upload';

export const routes: Routes = [
  {
    path: '',
    component: UploadPage,
    title: 'ReqAI - Upload'
  },
  {
    path: 'analyses/:documentId',
    component: AnalysisDetailPage,
    title: 'ReqAI - Analysis Result'
  },
  {
    path: '**',
    redirectTo: ''
  }
];