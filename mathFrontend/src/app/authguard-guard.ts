import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authguardGuard: CanActivateFn = () => {
  const router = inject(Router);

  if (!localStorage.getItem('_')?.length) {
    router.navigate(['login']);
    return false;
  }

  return true;
};
