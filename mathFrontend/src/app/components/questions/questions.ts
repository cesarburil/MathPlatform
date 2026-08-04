import { Component, resource, signal } from '@angular/core';
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
    questionsService.getAll().subscribe(result =>
      this.allQuestions.set(result)
    )
  }

  allQuestions = signal<QuestionResponse[] | null>(null);

}
