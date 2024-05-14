import { Component, Input } from '@angular/core';
import { VideoCardComponent } from '../video-card/video-card.component';
import { RouterLink } from '@angular/router';
import { VideoDtoCard } from '../videoDto';

@Component({
  selector: 'app-video-box-full',
  standalone: true,
  imports: [
    VideoCardComponent,
    RouterLink,
  ],
  templateUrl: './video-box-full.component.html',
  styleUrl: './video-box-full.component.css'
})
export class VideoBoxFullComponent {
  @Input() heading!: string;
  @Input() videos!: Array<VideoDtoCard>;
}
