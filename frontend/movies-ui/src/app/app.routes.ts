import { Routes } from '@angular/router';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { AppComponent } from './app.component';
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';
import { VideoDetailComponent } from './video-detail/video-detail.component';

export const routes: Routes = [
  {
    path: 'upload-video',
    component: UploadVideoComponent,
    title: 'Upload Video'
  },
  {
    path: 'save-video-details/:videoId',
    component: SaveVideoDetailsComponent,
    title: 'Save Video Details'
  },
  {
    path: 'video-details/:videoId',
    component: VideoDetailComponent,
    title: 'Video Details',
  }
  
];
