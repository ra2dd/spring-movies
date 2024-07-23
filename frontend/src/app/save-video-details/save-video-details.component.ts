import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatChipEditedEvent, MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { VideoService } from '../video.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { VideoPlayerComponent } from '../video-player/video-player.component';
import { VideoDtoDetailsForm, VideoStatus } from '../videoDto';

@Component({
  selector: 'app-save-video-details',
  standalone: true,
  imports: [
    MatSelectModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    VideoPlayerComponent,
  ],
  providers: [
    VideoService
  ],
  templateUrl: './save-video-details.component.html',
  styleUrl: './save-video-details.component.css'
})
export class SaveVideoDetailsComponent implements OnInit{

  videoId: string;
  tags: string[] = [];

  videoService = inject(VideoService);

  saveVideoDetailsForm = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    videoStatus: new FormControl(VideoStatus.PRIVATE),
  })

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  announcer = inject(LiveAnnouncer);

  constructor(
    private activatedRoute: ActivatedRoute, 
    private _snackBar: MatSnackBar
  ) {
    this.videoId = this.activatedRoute.snapshot.params["videoId"];
  }

  ngOnInit(): void {
    // Fetch video data and update FormGroup
    this.videoService.getVideoData(this.videoId).subscribe(data => {

      // @ts-ignore
      this.tags = data.tags;

      this.saveVideoDetailsForm.patchValue({
        title: data.title,
        description: data.description,
        videoStatus: data.videoStatus,
      })
    })
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add item
    if (value && !this.tags.includes(value)) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      // Remove 1 item at index
      this.tags.splice(index, 1);

      this.announcer.announce(`Removed: ${tag}`);
    }
  }

  edit(tag: string, event: MatChipEditedEvent) {
    const value = event.value.trim();

    // Remove element if it no longer has a name
    if (!value) {
      this.remove(tag);
      return;
    }

    // Edit exisitng element
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags[index] = value;
    }
  }

  selectedFile!: File;
  
  onFileSelected($event: Event) {

    // @ts-ignore
    if (!$event.target.files) {
      return;
    }

    // @ts-ignore
    this.selectedFile = $event.target.files[0];
  }

  uploadThumbnail() {
    this.videoService.postThumbnail(this.selectedFile, this.videoId)
      .subscribe(data => {
        console.log(data);
        // show a notification (upload successfull)
        this._snackBar.open('Thumbnail upload successfull.', "Ok");
      })
  }

  saveDetails() {
    const videoDetails: VideoDtoDetailsForm = {
      title: this.saveVideoDetailsForm.value.title ?? '',
      description: this.saveVideoDetailsForm.value.description ?? '',
      tags: this.tags,
      videoStatus: this.saveVideoDetailsForm.value.videoStatus ?? VideoStatus.PRIVATE,
    }

    this.videoService.putEditVideoMetadata(this.videoId, videoDetails).subscribe(data => {
      this._snackBar.open("Video details saved successfully", "Ok");
    })
  }
}
