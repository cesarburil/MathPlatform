import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CategoryResponse } from '../models/CategoryResponse';

@Injectable({
  providedIn: 'root',
})
export class CategoriesService {
  
  constructor(private httpClient: HttpClient, private router: Router) { };

  url = "http://localhost:8080";

  get():Observable<CategoryResponse[]>{
    return this.httpClient.get<CategoryResponse[]>(`${this.url}/categories/`);
  }

}
