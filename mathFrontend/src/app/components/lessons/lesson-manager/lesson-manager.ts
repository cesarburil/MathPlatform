import { Component, signal } from '@angular/core';
import { LessonsService } from '../../../services/lessons.service';
import { LessonResponse } from '../../../models/LessonResponse';
import { CategoriesService } from '../../../services/categories.service';
import { CategoryResponse } from '../../../models/CategoryResponse';

@Component({
  selector: 'app-lesson-manager',
  imports: [],
  templateUrl: './lesson-manager.html',
  styleUrl: './lesson-manager.scss',
})
export class LessonManager {
    constructor(private lessonsService: LessonsService, private categoriesService: CategoriesService) {}

    categories = signal<CategoryResponse[]>([]);
    lessons = signal<LessonResponse[]>([]);
    selectedCategory = signal<CategoryResponse | null>(null);
    lessonTitle = signal<string>('');
    lessonId = signal<number>(0);
    selectedLesson = signal<LessonResponse | null>(null);
    showForm = signal<boolean>(false);

    ngOnInit(): void {
        this.lessonsService.getAllLessons().subscribe((lessons) => this.lessons.set(lessons));
        this.categoriesService.get().subscribe((categories) => this.categories.set(categories));
    }

    create(): void {
        this.lessonsService.create({ title: this.lessonTitle(), categoryId: this.selectedCategory()?.id ?? 1, description: '', video: '' }).subscribe((lesson) => {
            this.lessons.update((lessons) => [...lessons, lesson]);
            this.lessonTitle.set('');
            this.lessonId.set(0);
        });
    }

    update(): void {
        this.lessonsService.update({ title: this.lessonTitle(), categoryId: this.selectedCategory()?.id ?? 1, description: '', video: '' }, this.lessonId()).subscribe((lesson) => {
            this.lessons.update((lessons) => lessons.map((lesson) => lesson.id === this.lessonId() ? lesson : lesson));
            this.lessonTitle.set('');
            this.lessonId.set(0);
        });
    }

    delete(lessonId: number): void {
        this.lessonsService.delete(lessonId).subscribe(() => {
            this.lessons.update((lessons) => lessons.filter((lesson) => lesson.id !== lessonId));
        });
    }

    closeForm(): void {
        this.lessonTitle.set('');
        this.lessonId.set(0);
        this.selectedLesson.set(null);
    }

    openForm(lesson: LessonResponse | null): void {
        this.showForm.set(true);
        if (lesson) {
            this.lessonTitle.set(lesson.title);
            this.lessonId.set(lesson.id);
            this.selectedCategory.set(this.categories().find((category) => category.id === lesson.categoryId) ?? null);
        } else {
            this.lessonTitle.set('');
            this.lessonId.set(0);
            this.selectedCategory.set(null);
        }
    }
}
