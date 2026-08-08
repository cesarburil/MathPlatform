import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LessonResponse } from '../models/LessonResponse';

@Injectable({
  providedIn: 'root',
})
export class LessonsService {

  constructor(private httpClient: HttpClient, private router: Router) { };

  get(catId: number): Observable<LessonResponse[]> {
    return this.httpClient.get<LessonResponse[]>(`${environment.apiUrl}/lessons/c/${catId}`)
  }

  getLessonById(lessonId: number): Observable<LessonResponse> {
    return this.httpClient.get<LessonResponse>(`${environment.apiUrl}/lessons/${lessonId}`)
  }

}
