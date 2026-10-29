import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { UserDto } from '../models/UserDto';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  readonly loggedIn = signal(!!localStorage.getItem('_')?.length);

  constructor(
    private httpClient: HttpClient,
    private router: Router,
  ) {}

  login(userDto: UserDto): Observable<string> {
    const data = { username: userDto.username, password: userDto.password };
    return this.httpClient
      .post<string>(`${environment.apiUrl}/login`, data, { responseType: 'text' as 'json' })
      .pipe(
        tap((token) => {
          localStorage.setItem('_', token);
          this.loggedIn.set(true);
        }),
      );
  }

  register(userDto: UserDto): Observable<string> {
    const data = { username: userDto.username, password: userDto.password };
    return this.httpClient.post<string>(`${environment.apiUrl}/register`, data, {
      responseType: 'text' as 'json',
    });
  }

  logout(): void {
    localStorage.removeItem('_');
    this.loggedIn.set(false);
    this.router.navigate(['/home']);
  }
}
