import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { UserDto } from '../../models/UserDto';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  constructor(private loginService: LoginService, private router: Router) {

    if (localStorage.getItem("_")?.length) {
      router.navigate(["/home"])
    }

  };

  userForm = new FormGroup({
    username: new FormControl<string>("", [Validators.required]),
    password: new FormControl<string>("", [Validators.required]),
  });

  register(): void {
    if (this.userForm.valid) {

      this.loginService.register(this.userForm.value as UserDto).subscribe((result) => {
        console.log(result);
        this.router.navigate(["home"]);
      })

    }
    else {
      alert("Not valid");
    }
  }


}

