import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentResponse } from '../models/CommentResponse';
import { environment } from '../../environments/environment';
import { CommentRequest } from '../models/CommentRequest';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
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
