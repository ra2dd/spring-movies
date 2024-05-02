import { Component, inject } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { VideoPlayerComponent } from '../video-player/video-player.component';

@Component({
  selector: 'app-video-detail',
  standalone: true,
  imports: [
    VideoPlayerComponent,
    NgIf, NgFor,
  ],
  templateUrl: './video-detail.component.html',
  styleUrl: './video-detail.component.css'
})
export class VideoDetailComponent {

  videoId: string = '';
  title: string = '';
  description: string = '';
  tags: string[] = [];
  videoUrl: string = '';

  videoService = inject(VideoService);

  constructor(
    private activatedRoute: ActivatedRoute, 
  ) {
    this.videoId = this.activatedRoute.snapshot.params["videoId"];
    this.videoService.getVideoData(this.videoId).subscribe(data => {
      this.title = data.title;
      this.description = data.description;
      // @ts-ignore
      this.tags = data.tags;
      this.videoUrl = data.videoUrl;
    })
  }
}
