import { Routes } from '@angular/router';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';
import { VideoDetailComponent } from './video-detail/video-detail.component';
import { HomeComponent } from './home/home.component';
import { SubscribedVideosComponent } from './subscribed-videos/subscribed-videos.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home',
  },
  {
    path: 'subscribed',
    component: SubscribedVideosComponent,
    title: 'Subscribed'
  },
  {
    path: 'upload-video',
    component: UploadVideoComponent,
    title: 'Upload Video'
  },
  {
    path: ':videoId',
    component: VideoDetailComponent,
    title: 'Video Details',
  },
  {
    path: ':videoId/edit',
    component: SaveVideoDetailsComponent,
    title: 'Save Video Details'
  }
];
