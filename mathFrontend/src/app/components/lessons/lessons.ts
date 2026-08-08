import { Component, inject, input, OnDestroy, OnInit, signal } from '@angular/core';
import { LessonsService } from '../../services/lessons.service';
import { LessonResponse } from '../../models/LessonResponse';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-lessons',
  imports: [RouterLink],
  templateUrl: './lessons.html',
  styleUrl: './lessons.scss',
})
export class Lessons implements OnInit {

  lessonsService = inject(LessonsService);

  categoryId = input.required<number>();

  ngOnInit(): void {

    this.lessonsService.get(this.categoryId()).subscribe(result =>
      this.lessons.set(result)
    )

  }


  lessons = signal<LessonResponse[] | null>(null);

}
