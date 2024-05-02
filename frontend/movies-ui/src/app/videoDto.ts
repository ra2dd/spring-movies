export interface VideoDto {
  id: string;
  title: string;
  description: string;
  userId: string;
  likes: number;
  dislikes: number;
  tags: string[];
  videoUrl: string;
  videoStatus: VideoStatus | string;
  viewCount: number;
  thumbnailUrl: string;
  commentList: string[];
}

export interface VideoDtoDetailsForm {
  id: string;
  title: string;
  description: string;
  tags: string[];
  videoStatus: VideoStatus | string;
}

enum VideoStatus {
  // Define the possible values for VideoStatus enum here
  PUBLIC,
  PRIVATE,
  UNLISTED,
}
