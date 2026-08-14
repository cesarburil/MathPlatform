import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminService {

  constructor (private httpClient: HttpClient) {}

  get():Observable<string> {
    return this.httpClient.get<string>(`${environment.apiUrl}/admin/`);
  }
  
}
