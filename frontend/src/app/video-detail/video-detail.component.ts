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
  likes: number = 0;
  tags: string[] = [];
  videoUrl: string = '';
  viewCount: number = 0;

  isvideoLiked: boolean = false;
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
      this.likes = data.likes;
    })
    this.videoService.postViewed(this.videoId).subscribe(data => {
      this.viewCount = data;
    })
    this.videoService.getIsVideoLikedByUser(this.videoId).subscribe(data => {
      this.isvideoLiked = data;
    })
  }

  likeVideo() {
    this.videoService.postLike(this.videoId).subscribe(data => {
      this.likes = data;
      this.isvideoLiked = !this.isvideoLiked;
    })
  }
}
