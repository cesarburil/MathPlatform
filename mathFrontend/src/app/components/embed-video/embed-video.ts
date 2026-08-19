import { Component, computed, input } from '@angular/core';
import { YouTubePlayer } from '@angular/youtube-player';
import { youtubeVideoId } from '../../youtube';

@Component({
  selector: 'app-embed-video',
  imports: [YouTubePlayer],
  templateUrl: './embed-video.html',
  styleUrl: './embed-video.scss',
})
export class EmbedVideo {
  videoLink = input.required<string>();
  cinema = input(false);

  videoId = computed(() => youtubeVideoId(this.videoLink()));

  readonly playerVars = {
    rel: 0,
    modestbranding: 1,
    iv_load_policy: 3,
    fs: 1,
  };
}
