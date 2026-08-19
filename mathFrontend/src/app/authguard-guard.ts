import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from './services/login.service';

export const authguardGuard: CanActivateFn = () => {
  const router = inject(Router);

  if (!localStorage.getItem("_")?.length) {
    router.navigate(["login"]);
    return false;
  }

  return true;
};

export const payGuard: CanActivateFn = () => {
  const login = inject(LoginService);
  const router = inject(Router);

  if (!localStorage.getItem("_")?.length) {
    router.navigate(["login"]);
    return false;
  }

  if (login.hasPremium()) {
    router.navigate(["home"]);
    return false;
  }

  return true;
};
