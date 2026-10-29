import { Component, signal } from '@angular/core';
import { QuestionsService } from '../../services/questions.service';
import { QuestionResponse } from '../../models/QuestionResponse';

@Component({
  selector: 'app-questions',
  imports: [],
  templateUrl: './questions.html',
  styleUrl: './questions.scss',
})
export class Questions {
  constructor(private questionsService: QuestionsService) {
    questionsService.getAll().subscribe((result) => this.allQuestions.set(result));
  }

  allQuestions = signal<QuestionResponse[] | null>(null);
  selectedQuestionId = signal(0);
  selectedAltId = signal(0);
  verdict = signal<Record<number, boolean>>({});

  difficultyLabel(value: string): string {
    if (value === 'EASY') return 'Fácil';
    if (value === 'MEDIUM') return 'Médio';
    if (value === 'HARD') return 'Difícil';
    return value;
  }

  select(questionId: number, altId: number): void {
    this.selectedQuestionId.set(questionId);
    this.selectedAltId.set(altId);
  }

  verify(): void {
    const questionId = this.selectedQuestionId();
    this.questionsService
      .verify({
        questionId,
        alternativeId: this.selectedAltId(),
      })
      .subscribe((result) => {
        this.verdict.update((current) => ({ ...current, [questionId]: result }));
      });
  }
}
