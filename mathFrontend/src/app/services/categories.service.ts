import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CategoryResponse } from '../models/CategoryResponse';
import { environment } from '../../environments/environment';
import { CategoryRequest } from '../models/CategoryRequest';

@Injectable({
  providedIn: 'root',
})
export class CategoriesService {
  
  constructor(private httpClient: HttpClient, private router: Router) { };


  get():Observable<CategoryResponse[]>{
    return this.httpClient.get<CategoryResponse[]>(`${environment.apiUrl}/categories/?quantity=15`);
  }

  create(category: CategoryRequest):Observable<CategoryResponse>{
    return this.httpClient.post<CategoryResponse>(`${environment.apiUrl}/categories/create`, category)
  }

  update(category: CategoryRequest, id: number):Observable<CategoryResponse>{
    return this.httpClient.put<CategoryResponse>(`${environment.apiUrl}/categories/update/${id}`, category)
  }

  delete(id: number):Observable<string>{
    return this.httpClient.delete<string>(`${environment.apiUrl}/categories/delete/${id}`)
  }

}
