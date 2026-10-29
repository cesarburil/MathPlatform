import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { UserDto } from '../../models/UserDto';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  constructor(
    private loginService: LoginService,
    private router: Router,
  ) {
    if (localStorage.getItem('_')?.length) {
      this.loginService.loggedIn.set(true);
      router.navigate(['home']);
    }
  }

  userForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  login(): void {
    if (!this.userForm.valid) {
      alert('Preencha usuário e senha.');
      return;
    }

    this.loginService.login(this.userForm.value as UserDto).subscribe({
      next: () => this.router.navigate(['home']),
      error: (e) => {
        if (e.status === 0) {
          alert('API fora do ar. Suba o backend em http://localhost:8081');
          return;
        }
        const msg =
          typeof e.error === 'string'
            ? e.error
            : e.error?.message || e.message || 'Falha no login';
        alert(msg);
      },
    });

  }
}
