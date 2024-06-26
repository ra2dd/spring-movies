export interface VideoDto {
  id: string;
  title: string;
  description: string;
  username: string;
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

export interface VideoDtoCard {
  id: string;
  title: string;
  thumbnailUrl: string;
  username: string;
}

enum VideoStatus {
  // Define the possible values for VideoStatus enum here
  PUBLIC,
  PRIVATE,
  UNLISTED,
}

export function mapVideoDtoArrayToVideoDtoCardArray(
  data: VideoDto[],
  videos: Array<VideoDtoCard>) {
    data.forEach((video) => {
      const videoDtoCard: VideoDtoCard = {
        id: video.id,
        title: video.title,
        thumbnailUrl: video.thumbnailUrl,
        username: video.username,
      };
      videos.push(videoDtoCard);
    });
}
