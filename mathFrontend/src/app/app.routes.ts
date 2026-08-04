import { ActivatedRoute, Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Login } from './components/login/login';
import { authguardGuard } from './authguard-guard';
import { Register } from './components/register/register';
import { Payment } from './components/payment/payment';
import { Categories } from './components/categories/categories';
import { About } from './components/about/about';

export const routes: Routes = [
  { path: "login", component: Login },
  { path: "register", component: Register },
  { path: "about", component: About },
  {
    path: "", canActivate: [authguardGuard], children: [
      { path: "", pathMatch: "full", redirectTo: "home" },
      { path: "home", component: Home },
      { path: "pay", component: Payment},
      { path: "categories", component: Categories},
    ]
  }
];
