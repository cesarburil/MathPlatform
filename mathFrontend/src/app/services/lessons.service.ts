import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LessonResponse } from '../models/LessonResponse';
import { LessonRequest } from '../models/LessonRequest';

@Injectable({
  providedIn: 'root',
})
export class LessonsService {

  constructor(private httpClient: HttpClient, private router: Router) { };

  getAllLessons(): Observable<LessonResponse[]> {
    return this.httpClient.get<LessonResponse[]>(`${environment.apiUrl}/lessons/`);
  }

  get(catId: number): Observable<LessonResponse[]> {
    return this.httpClient.get<LessonResponse[]>(`${environment.apiUrl}/lessons/c/${catId}`)
  }

  getLessonById(lessonId: number): Observable<LessonResponse> {
    return this.httpClient.get<LessonResponse>(`${environment.apiUrl}/lessons/${lessonId}`)
  }

  create(lesson: LessonRequest):Observable<LessonResponse>{
      return this.httpClient.post<LessonResponse>(`${environment.apiUrl}/lessons/create`, lesson)
    }
  
    update(lesson: LessonRequest, id: number):Observable<LessonResponse>{
      return this.httpClient.put<LessonResponse>(`${environment.apiUrl}/lessons/update/${id}`, lesson)
    }
  
    delete(id: number):Observable<string>{
      return this.httpClient.delete<string>(`${environment.apiUrl}/lessons/delete/${id}`)
    }

}
