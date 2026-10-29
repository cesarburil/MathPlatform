import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { VerifiedQuestionRequest } from '../models/VerifiedQuestionRequest';
import { QuestionResponse } from '../models/QuestionResponse';

@Injectable({
  providedIn: 'root',
})
export class QuestionsService {
  constructor(private httpClient: HttpClient) { };

  getAll():Observable<QuestionResponse[]> {
    return this.httpClient.get<QuestionResponse[]>(`${environment.apiUrl}/questions/`)
  }

  verify(verifiedQuestion: VerifiedQuestionRequest): Observable<boolean> {
    return this.httpClient.post<boolean>(`${environment.apiUrl}/questions/verify`, verifiedQuestion)
  }
}
