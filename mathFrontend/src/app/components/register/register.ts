import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { ToastService } from '../../services/toast.service';
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
    private toasts: ToastService,
  ) {
    if (localStorage.getItem("_")?.length) {
      router.navigate(["/home"])
    }
  };

  userForm = new FormGroup({
    username: new FormControl<string>("", [Validators.required]),
    password: new FormControl<string>("", [Validators.required]),
  });

  register(): void {
    if (!this.userForm.valid) {
      this.toasts.error('Preencha usuário e senha.');
      return;
    }

    this.loginService.register(this.userForm.value as UserDto).subscribe({
      next: () => {
        this.toasts.ok('Conta criada. Entre para continuar.');
        this.router.navigate(["login"]);
      },
    });
  }
}
