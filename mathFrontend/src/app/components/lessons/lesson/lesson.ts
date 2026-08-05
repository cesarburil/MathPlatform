import { Component, inject, input, OnInit, signal } from '@angular/core';
import { LessonsService } from '../../../services/lessons.service';
import { LessonResponse } from '../../../models/LessonResponse';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-lesson',
  imports: [RouterLink],
  templateUrl: './lesson.html',
  styleUrl: './lesson.scss',
})
export class Lesson implements OnInit {


  lessonsService = inject(LessonsService);

  lessonId = input.required<number>();

  lesson = signal<LessonResponse | null>(null);

  ngOnInit(): void {

    this.lessonsService.getLessonById(this.lessonId()).subscribe(result =>
      this.lesson.set(result)
    )

  }

}
