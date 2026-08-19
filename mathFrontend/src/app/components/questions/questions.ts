import { Component, signal } from '@angular/core';
import { QuestionsService } from '../../services/questions.service';
import { QuestionResponse } from '../../models/QuestionResponse';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-questions',
  imports: [],
  templateUrl: './questions.html',
  styleUrl: './questions.scss',
})
export class Questions {
  constructor(
    private questionsService: QuestionsService,
    private toasts: ToastService,
  ) {
    questionsService.getAll().subscribe(result =>
      this.allQuestions.set(result)
    )
  }

  allQuestions = signal<QuestionResponse[] | null>(null);

  selectedQuestionId = signal<number>(0);
  selectedAltId = signal<number>(0);
  verdict = signal<Record<number, boolean>>({});

  difficultyLabel(value: string): string {
    if (value === 'EASY') {
      return 'Fácil';
    }
    if (value === 'MEDIUM') {
      return 'Médio';
    }
    if (value === 'HARD') {
      return 'Difícil';
    }
    return value;
  }

  verify() {
    if (!this.selectedQuestionId() || !this.selectedAltId()) {
      this.toasts.error('Escolha uma alternativa antes de verificar.');
      return;
    }
    this.questionsService.verify({
      questionId: this.selectedQuestionId(),
      alternativeId: this.selectedAltId()
    }).subscribe(result =>
      this.verdict.update((current) => ({
        ...current,
        [this.selectedQuestionId()]: result
      }))
    );
  }

}
