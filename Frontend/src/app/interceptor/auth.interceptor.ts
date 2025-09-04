import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth.service";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  //if (req.url.includes('/auth') || !authService.getToken()) {
  //  return next(req);
  //}

  //const authReq = req.clone({
  //  setHeaders: {
  //    Authorization: `Bearer ${authService.getToken()}`
  //  }
  //});
  const token = localStorage.getItem('auth_token');
  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(cloned);
  }

  //console.log('Added auth header to request:', authReq);
  return next(req);
}
