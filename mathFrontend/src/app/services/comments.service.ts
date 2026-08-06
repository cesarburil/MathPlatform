import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentResponse } from '../models/CommentResponse';
import { environment } from '../../environments/environment';
import { CommentRequest } from '../models/CommentRequest';
import { AnswerRequest } from '../models/AnswerRequest';
import { AnswerResponse } from '../models/AnswerResponse';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  addAnswer(answerRequest: AnswerRequest): Observable<AnswerResponse> {
    return this.httpClient.post<AnswerResponse>(`${environment.apiUrl}/answers/create`, answerRequest
    );
  }
  constructor(private httpClient: HttpClient) { };

  getAll(): Observable<CommentResponse[]> {
    return this.httpClient.get<CommentResponse[]>(`${environment.apiUrl}/comments/`)
  }

  addComment({title}: CommentRequest): Observable<CommentResponse> {
    return this.httpClient.post<CommentResponse>(`${environment.apiUrl}/comments/create`, {title});
  }

  getCommentById(id: number): Observable<CommentResponse> {
    return this.httpClient.get<CommentResponse>(`${environment.apiUrl}/comments/${id}`);
  }

}
