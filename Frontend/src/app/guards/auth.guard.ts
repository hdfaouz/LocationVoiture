import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth.service";

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  console.log('AuthGuard vérifie l\'authentification...');

  // Vérifier si on est dans le navigateur (pas en SSR)
  if (typeof window === 'undefined' || typeof localStorage === 'undefined') {
    console.log('Environnement serveur détecté, accès refusé');
    return false;
  }

  console.log('Utilisateur connecté ?', authService.isLoggedIn());

  if (authService.isLoggedIn()) {
    console.log('Accès autorisé à:', state.url);
    return true;
  } else {
    console.log('Accès refusé, redirection vers /login');
    router.navigate(['/login']);
    return false;
  }
}
