import { Routes } from '@angular/router';

import { AnalysisDetailPage } from './pages/analysis-detail/analysis-detail';
import { HistoryPage } from './pages/history/history';
import { UploadPage } from './pages/upload/upload';

export const routes: Routes = [
  {
    path: 'history',
    component: HistoryPage,
    title: 'ReqAI - Analysis History'
  },
  {
    path: 'analyses/:documentId',
    component: AnalysisDetailPage,
    title: 'ReqAI - Analysis Result'
  },
  {
    path: '',
    pathMatch: 'full',
    component: UploadPage,
    title: 'ReqAI - Upload'
  },

  {
  path: '**',
  redirectTo: ''
}
];