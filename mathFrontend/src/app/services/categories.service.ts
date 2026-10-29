import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CategoryResponse } from '../models/CategoryResponse';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CategoriesService {
  
  constructor(private httpClient: HttpClient, private router: Router) { };


  get():Observable<CategoryResponse[]>{
    return this.httpClient.get<CategoryResponse[]>(`${environment.apiUrl}/categories/`);
  }

}
