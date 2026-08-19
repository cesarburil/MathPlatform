import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  constructor(private httpClient: HttpClient) { };

  pay(encryptedCard: string): Observable<string> {
    return this.httpClient.post<string>(`${environment.apiUrl}/pay`, encryptedCard, { responseType: 'text' as 'json' })
  }

}
