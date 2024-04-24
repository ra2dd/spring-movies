import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatChipEditedEvent, MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { VideoService } from '../video.service';
import { ActivatedRoute } from '@angular/router';

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
  ],
  providers: [
    VideoService
  ],
  templateUrl: './save-video-details.component.html',
  styleUrl: './save-video-details.component.css'
})
export class SaveVideoDetailsComponent {

  videoId: string;
  videoService = inject(VideoService);

  saveVideoDetailsForm = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    videoStatus: new FormControl(''),
  })

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  announcer = inject(LiveAnnouncer);

  tags: String[] = [];

  constructor(private activatedRoute: ActivatedRoute) {
    this.videoId = this.activatedRoute.snapshot.params["videoId"];
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: String): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      // Remove 1 item at index
      this.tags.splice(index, 1);

      this.announcer.announce(`Removed: ${tag}`);
    }
  }

  edit(tag: String, event: MatChipEditedEvent) {
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
      })
  }
}
