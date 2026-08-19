import { Component, computed, inject, input, signal } from '@angular/core';
import { takeUntilDestroyed, toObservable } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
import { map, switchMap, tap } from 'rxjs';
import { LessonResponse } from '../../../models/LessonResponse';
import { LessonsService } from '../../../services/lessons.service';
import { EmbedVideo } from '../../embed-video/embed-video';

const notesKey = (lessonId: number) => `mp-lesson-notes:${lessonId}`;

function readNotes(lessonId: number): string {
  try {
    return localStorage.getItem(notesKey(lessonId)) ?? '';
  } catch {
    return '';
  }
}

function writeNotes(lessonId: number, text: string): void {
  try {
    const key = notesKey(lessonId);
    if (!text) {
      localStorage.removeItem(key);
      return;
    }
    localStorage.setItem(key, text);
  } catch {
    // quota cheia ou modo privado: a nota segue só nesta tela
  }
}

@Component({
  selector: 'app-lesson',
  imports: [RouterLink, EmbedVideo],
  templateUrl: './lesson.html',
  styleUrl: './lesson.scss',
})
export class Lesson {
  private readonly lessonsService = inject(LessonsService);

  readonly lessonId = input.required<number>();

  readonly lesson = signal<LessonResponse | null>(null);
  readonly siblings = signal<LessonResponse[]>([]);
  readonly notes = signal('');

  readonly previous = computed(() => {
    const current = this.lesson();
    const list = this.siblings();
    if (!current) {
      return null;
    }
    const index = list.findIndex((item) => item.id === current.id);
    return index > 0 ? list[index - 1] : null;
  });

  readonly next = computed(() => {
    const current = this.lesson();
    const list = this.siblings();
    if (!current) {
      return null;
    }
    const index = list.findIndex((item) => item.id === current.id);
    return index >= 0 && index < list.length - 1 ? list[index + 1] : null;
  });

  constructor() {
    toObservable(this.lessonId)
      .pipe(
        map((id) => Number(id)),
        tap((id) => this.notes.set(readNotes(id))),
        switchMap((id) =>
          this.lessonsService.getLessonById(id).pipe(
            switchMap((lesson) =>
              this.lessonsService.get(lesson.categoryId).pipe(
                map((siblings) => ({ lesson, siblings })),
              ),
            ),
          ),
        ),
        takeUntilDestroyed(),
      )
      .subscribe(({ lesson, siblings }) => {
        this.lesson.set(lesson);
        this.siblings.set(siblings);
      });
  }

  onNotes(event: Event): void {
    const text = (event.target as HTMLTextAreaElement).value;
    this.saveNotes(text);
  }

  clearNotes(): void {
    this.saveNotes('');
  }

  private saveNotes(text: string): void {
    this.notes.set(text);
    writeNotes(Number(this.lessonId()), text);
  }
}
