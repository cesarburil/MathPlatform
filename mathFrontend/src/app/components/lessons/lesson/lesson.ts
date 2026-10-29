import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { RouterLink } from '@angular/router';
import { LessonsService } from '../../../services/lessons.service';
import { LessonResponse } from '../../../models/LessonResponse';

@Component({
  selector: 'app-lesson',
  imports: [RouterLink],
  templateUrl: './lesson.html',
  styleUrl: './lesson.scss',
})
export class Lesson implements OnInit {
  private readonly lessonsService = inject(LessonsService);
  private readonly sanitizer = inject(DomSanitizer);

  readonly lessonId = input.required<number>();
  readonly lesson = signal<LessonResponse | null>(null);

  readonly safeVideo = computed<SafeResourceUrl | null>(() => {
    const raw = this.lesson()?.video;
    if (!raw) {
      return null;
    }
    return this.sanitizer.bypassSecurityTrustResourceUrl(this.toEmbedUrl(raw));
  });

  ngOnInit(): void {
    this.lessonsService.getLessonById(this.lessonId()).subscribe((result) => this.lesson.set(result));
  }

  private toEmbedUrl(url: string): string {
    try {
      const parsed = new URL(url);
      if (parsed.hostname.includes('youtu.be')) {
        return `https://www.youtube.com/embed/${parsed.pathname.replace('/', '')}`;
      }
      const id = parsed.searchParams.get('v');
      if (id) {
        return `https://www.youtube.com/embed/${id}`;
      }
    } catch {
      /* keep raw */
    }
    return url;
  }
}
