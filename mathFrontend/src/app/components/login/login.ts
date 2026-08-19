import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { ToastService } from '../../services/toast.service';
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
    private toasts: ToastService,
  ) {
    if (localStorage.getItem("_")?.length) {
      router.navigate(["home"])
    }
  };

  userForm = new FormGroup({
    username: new FormControl<string>("", [Validators.required]),
    password: new FormControl<string>("", [Validators.required]),
  });

  loginDemo(): void {
    this.loginService.enterDemo();
  }

  login(): void {
    if (!this.userForm.valid) {
      this.toasts.error('Preencha usuário e senha.');
      return;
    }

    this.loginService.login(this.userForm.value as UserDto).subscribe({
      next: (result) => {
        this.loginService.setSession(result);
        this.router.navigate(["home"]);
      },
    });
  }
}
