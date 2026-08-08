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

  constructor(private loginService: LoginService, private router: Router) {

    if (localStorage.getItem("_")?.length) {
      router.navigate(["home"])
    }

  };

  userForm = new FormGroup({
    username: new FormControl<string>("", [Validators.required]),
    password: new FormControl<string>("", [Validators.required]),
  });

  login(): void {
    if (this.userForm.valid) {

      this.loginService.login(this.userForm.value as UserDto).subscribe(
        {
          next: (result) => {
            console.log(result);
            localStorage.setItem("_", result);
            this.router.navigate(["home"])
          },
          error: (e) => {
            alert(e.error);
          }
        }
      )

    }
    else {
      alert("Not valid");
    }
  }


}
