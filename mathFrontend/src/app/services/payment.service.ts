import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  constructor(private httpClient: HttpClient) { };

  url = "http://localhost:8080";

  pay(encryptedCard: string): Observable<string> {
    console.log(encryptedCard);
    return this.httpClient.post<string>(`${this.url}/pay`, encryptedCard, { responseType: 'text' as 'json' })
  }

}
