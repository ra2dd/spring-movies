import { Component, inject } from '@angular/core';
import { VideoService } from '../video.service';
import { VideoDtoCard, mapVideoDtoArrayToVideoDtoCardArray } from '../videoDto';
import { VideoBoxFullComponent } from '../video-box-full/video-box-full.component';


@Component({
  selector: 'app-subscribed-videos',
  standalone: true,
  imports: [
    VideoBoxFullComponent,
  ],
  templateUrl: './subscribed-videos.component.html',
  styleUrl: './subscribed-videos.component.css'
})
export class SubscribedVideosComponent {
  videoService = inject(VideoService);

  heading: string = "Subscribed videos";
  videos: Array<VideoDtoCard> = [];

  constructor() {
    this.videoService.getSubscribedVideos().subscribe(data => {
      mapVideoDtoArrayToVideoDtoCardArray(data, this.videos);
    });
  }
}
