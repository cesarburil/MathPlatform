import { ActivatedRoute, Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Login } from './components/login/login';
import { authguardGuard } from './authguard-guard';
import { Register } from './components/register/register';
import { Payment } from './components/payment/payment';
import { Categories } from './components/categories/categories';
import { About } from './components/about/about';
import { Questions } from './components/questions/questions';
import { CheckPayment } from './components/check-payment/check-payment';
import { Lessons } from './components/lessons/lessons';

export const routes: Routes = [
  { path: "login", component: Login },
  { path: "register", component: Register },
  { path: "about", component: About },
  {
    path: "", canActivate: [authguardGuard], children: [
      { path: "", pathMatch: "full", redirectTo: "home" },
      { path: "home", component: Home },
      { path: "pay", component: Payment},
      { path: "checkPayment", component: CheckPayment},
      { path: "categories", component: Categories},
      { path: "lessons/:categoryId", component: Lessons},
      { path: "questions", component: Questions}
    ]
  }
];
