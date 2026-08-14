import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { VerifiedQuestionRequest } from '../models/VerifiedQuestionRequest';
import { QuestionResponse } from '../models/QuestionResponse';
import { QuestionRequest } from '../models/QuestionRequest';

@Injectable({
  providedIn: 'root',
})
export class QuestionsService {
  constructor(private httpClient: HttpClient) { };

  getAll(): Observable<QuestionResponse[]> {
    return this.httpClient.get<QuestionResponse[]>(`${environment.apiUrl}/questions/`)
  }

  verify(verifiedQuestion: VerifiedQuestionRequest): Observable<boolean> {
    return this.httpClient.post<boolean>(`${environment.apiUrl}/questions/verify`, verifiedQuestion)
  }


  create(question: QuestionRequest): Observable<QuestionResponse> {
    return this.httpClient.post<QuestionResponse>(`${environment.apiUrl}/questions/create`, question)
  }

  update(question: QuestionRequest, id: number): Observable<QuestionResponse> {
    return this.httpClient.put<QuestionResponse>(`${environment.apiUrl}/questions/update/${id}`, question)
  }

  delete(id: number): Observable<string> {
    return this.httpClient.delete<string>(`${environment.apiUrl}/questions/delete/${id}`)
  }

}
