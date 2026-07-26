import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../models/UserDto';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private httpClient: HttpClient, private router: Router) { };

  login(userDto: UserDto): Observable<string> {
    var data = { username: userDto.username, password: userDto.password };
    return this.httpClient.post<string>(`${environment.apiUrl}/login`, data, { responseType: 'text' as 'json' })
  }

  register(userDto: UserDto): Observable<string> {
    var data = { username: userDto.username, password: userDto.password };
    return this.httpClient.post<string>(`${environment.apiUrl}/register`, data, { responseType: 'text' as 'json' })
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

}
