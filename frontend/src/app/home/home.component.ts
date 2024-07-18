import { Component, inject } from '@angular/core';
import { VideoService } from '../video.service';
import { VideoDtoCard, mapVideoDtoArrayToVideoDtoCardArray } from '../videoDto';
import { VideoBoxFullComponent } from '../video-box-full/video-box-full.component';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    VideoBoxFullComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  videoService = inject(VideoService);

  heading: string = "All videos";
  videos: Array<VideoDtoCard> = [];

  constructor() {
    this.videoService.getAllVideos().subscribe(data => {
      mapVideoDtoArrayToVideoDtoCardArray(data, this.videos);
    });
  }
}
