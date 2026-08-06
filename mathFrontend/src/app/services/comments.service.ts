import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentResponse } from '../models/CommentResponse';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  constructor(private httpClient: HttpClient) { };

  getAll(): Observable<CommentResponse[]> {
    return this.httpClient.get<CommentResponse[]>(`${environment.apiUrl}/questions/`)
  }
}
