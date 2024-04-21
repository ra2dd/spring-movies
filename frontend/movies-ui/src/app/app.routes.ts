import { Routes } from '@angular/router';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { AppComponent } from './app.component';

export const routes: Routes = [
  {
    path: '',
    component: AppComponent,
    title: 'Home Page'
  },
  {
    path: 'upload-video',
    component: UploadVideoComponent,
    title: 'Upload Video'
  }
];
