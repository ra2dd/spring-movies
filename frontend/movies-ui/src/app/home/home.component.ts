import { Component, inject } from '@angular/core';
import { VideoCardComponent } from '../video-card/video-card.component';
import { VideoService } from '../video.service';
import { VideoDtoCard } from '../videoDto';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    VideoCardComponent,
    RouterLink
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  videoService = inject(VideoService);

  videos: Array<VideoDtoCard> = [];

  constructor() {
    this.videoService.getAllVideos().subscribe(data => {
      data.forEach((video) => {
        const videoDtoCard: VideoDtoCard = {
          id: video.id,
          title: video.title,
          thumbnailUrl: video.thumbnailUrl,
          username: video.username,
        };
        this.videos.push(videoDtoCard)
      })
    });
  }
}
