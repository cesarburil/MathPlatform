import { Component, signal } from '@angular/core';
import { QuestionResponse } from '../../../models/QuestionResponse';
import { QuestionsService } from '../../../services/questions.service';
import { AlternativeRequest } from '../../../models/AlternativeRequest';
import { CategoriesService } from '../../../services/categories.service';
import { CategoryResponse } from '../../../models/CategoryResponse';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { QuestionRequest } from '../../../models/QuestionRequest';

@Component({
  selector: 'app-question-manager',
  imports: [ReactiveFormsModule],
  templateUrl: './question-manager.html',
  styleUrl: './question-manager.scss',
})
export class QuestionManager {
  constructor(
    private questionsService: QuestionsService,
    private categoriesService: CategoriesService,
  ) {}

  questions = signal<QuestionResponse[]>([]);
  selectedQuestion = signal<QuestionResponse | null>(null);
  questionId = signal<number>(0);
  showForm = signal<boolean>(false);
  difficulties = ['EASY', 'MEDIUM', 'HARD'];
  categories = signal<CategoryResponse[]>([]);
  description = signal<string>('');
  alternatives = signal<AlternativeRequest[]>([]);

  ngOnInit(): void {
    this.questionsService.getAll().subscribe((questions) => this.questions.set(questions));
    this.categoriesService.get().subscribe((categories) => this.categories.set(categories));
  }

  questionForm = new FormGroup({
    title: new FormControl(''),
    categoryId: new FormControl(0),
    description: new FormControl(''),
    difficulty: new FormControl(''),
    video: new FormControl(''),
    alternatives: new FormControl([]),
  });

  addAlternative(): void {
    this.alternatives.update((alternatives) => [
      ...alternatives,
      { title: '', correct: false } as unknown as AlternativeRequest,
    ]);
  }

  create(): void {
    this.questionForm.patchValue({
      alternatives: this.alternatives() as unknown as never[],
    });

    console.log(this.questionForm.value);
    this.questionsService
      .create(this.questionForm.value as QuestionRequest)
      .subscribe((question) => {
        this.questions.update((questions) => [...questions, question]);
        this.questionForm.reset();
      });

    this.showForm.set(false);
  }

  delete(questionId: number): void {
    this.questionsService.delete(questionId).subscribe(() => {
      this.questions.update((questions) =>
        questions.filter((question) => question.id !== questionId),
      );
    });
  }

  openForm(question: QuestionResponse | null): void {
    this.showForm.set(!this.showForm());
    this.questionForm.reset();
    if (question) {
      this.questionForm.patchValue({
        title: question.title,
        categoryId: question.categoryId,
        difficulty: question.difficulty,
        video: question.video,
        alternatives: question.alternatives as unknown as never[],
      });
    }
  }

  closeForm(): void {
    this.questionForm.reset();
    this.showForm.set(false);
  }
}
