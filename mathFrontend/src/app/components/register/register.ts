import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Router, RouterLink } from '@angular/router';
import { UserDto } from '../../models/UserDto';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  constructor(
    private loginService: LoginService,
    private router: Router,
  ) {
    if (localStorage.getItem('_')?.length) {
      this.loginService.loggedIn.set(true);
      router.navigate(['/home']);
    }
  }

  userForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  register(): void {
    if (!this.userForm.valid) {
      alert('Preencha usuário e senha.');
      return;
    }

    this.loginService.register(this.userForm.value as UserDto).subscribe({
      next: () => this.router.navigate(['login']),
      error: (e) => alert(e.error || 'Falha no cadastro'),
    });
  }
}
