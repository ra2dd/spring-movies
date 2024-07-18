import { Component, Input } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { VideoDtoCard } from '../videoDto';

@Component({
  selector: 'app-video-card',
  standalone: true,
  imports: [
    MatCardModule
  ],
  templateUrl: './video-card.component.html',
  styleUrl: './video-card.component.css'
})
export class VideoCardComponent {
  @Input() videoDtoCard!: VideoDtoCard;
}
