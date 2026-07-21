import { Routes } from '@angular/router';

import { UploadPage } from './pages/upload/upload';

export const routes: Routes = [
  {
    path: '',
    component: UploadPage,
    title: 'ReqAI - Upload'
  },
  {
    path: '**',
    redirectTo: ''
  }
];