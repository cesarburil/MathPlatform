import { computed, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../models/UserDto';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { readJwtRole } from '../session';
import { DEMO_PASSWORD, DEMO_USERNAME } from '../catalog';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  readonly loggedIn = signal(false);
  readonly role = signal<string | null>(null);
  readonly hasPremium = computed(() => {
    const role = this.role();
    return role === 'PREMIUM' || role === 'ADMIN';
  });

  constructor(private httpClient: HttpClient, private router: Router) {
    this.refreshFromStorage();
  }

  login(userDto: UserDto): Observable<string> {
    var data = { username: userDto.username, password: userDto.password };
    return this.httpClient.post<string>(`${environment.apiUrl}/login`, data, { responseType: 'text' as 'json' })
  }

  register(userDto: UserDto): Observable<string> {
    var data = { username: userDto.username, password: userDto.password };
    return this.httpClient.post<string>(`${environment.apiUrl}/register`, data, { responseType: 'text' as 'json' })
  }

  setSession(token: string): void {
    localStorage.setItem("_", token);
    this.refreshFromStorage();
  }

  markPremium(): void {
    localStorage.setItem('mp-premium', '1');
    this.role.set('PREMIUM');
  }

  clearSession(): void {
    localStorage.removeItem("_");
    localStorage.removeItem('mp-premium');
    this.loggedIn.set(false);
    this.role.set(null);
  }

  logout(): void {
    this.clearSession();
    this.router.navigate(["/home"]);
  }

  enterDemo(): void {
    this.login({ username: DEMO_USERNAME, password: DEMO_PASSWORD }).subscribe({
      next: (token) => {
        this.setSession(token);
        this.router.navigate(['/categories']);
      },
    });
  }

  refreshFromStorage(): void {
    const token = localStorage.getItem("_");
    if (!token?.length) {
      this.loggedIn.set(false);
      this.role.set(null);
      return;
    }
    this.loggedIn.set(true);
    const jwtRole = readJwtRole(token);
    if (jwtRole === 'ADMIN') {
      this.role.set('ADMIN');
    } else if (jwtRole === 'PREMIUM' || localStorage.getItem('mp-premium') === '1') {
      this.role.set('PREMIUM');
    } else {
      this.role.set(jwtRole);
    }
  }
}
