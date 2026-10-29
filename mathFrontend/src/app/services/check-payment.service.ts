import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CheckPaymentService {

  constructor(private httpClient: HttpClient) {};

  check():Observable<any>{
    return this.httpClient.get<any>(`${environment.apiUrl}/sse`);
  }
}
