import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { map } from 'rxjs';

export const adminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.loadCurrentUser().pipe(
    map(user => {
      if (user.role === 'ADMIN') {
        return true;
      }

      router.navigate(['/tasks']);
      return false;
    })
  );
};
