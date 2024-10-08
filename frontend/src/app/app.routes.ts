import { Routes } from '@angular/router';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { EditVideoMetadataComponent } from './edit-video-metadata/edit-video-metadata.component';
import { VideoDetailComponent } from './video-detail/video-detail.component';
import { HomeComponent } from './home/home.component';
import { SubscribedVideosComponent } from './subscribed-videos/subscribed-videos.component';
import { authGuard } from './auth.guard';
import { AuthCallbackComponent } from './auth-callback/auth-callback.component';
import { RedirectComponent } from './redirect/redirect.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home',
  },
  {
    path: 'subscribed',
    component: SubscribedVideosComponent,
    title: 'Subscribed',
    canActivate: [authGuard],
  },
  {
    path: 'upload-video',
    component: UploadVideoComponent,
    title: 'Upload Video',
    canActivate: [authGuard],
  },
  {
    path: 'redirect',
    component: RedirectComponent,
  },
  {
    path: 'callback',
    component: AuthCallbackComponent,
  },
  {
    path: ':videoId',
    component: VideoDetailComponent,
    title: 'Video Details',
  },
  {
    path: ':videoId/edit',
    component: EditVideoMetadataComponent,
    title: 'Edit Video Metadata',
    canActivate: [authGuard],
  },
];