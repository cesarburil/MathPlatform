import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authguardGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);

  if (!localStorage.getItem("_")?.length) {
    router.navigate(["login"]);
  }

  return true;
};
