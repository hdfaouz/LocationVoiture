import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  role: "USER";
}

export interface LoginResponse {
  token: string;
  user: User;
}

export interface User{
  email: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = "http://localhost:8080";

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    const user = this.getUserFromStorage();
    if (user) {
      this.currentUserSubject.next(user);
    }
  }

  private getUserFromStorage(): User | null {
    if (typeof Storage !== 'undefined' && localStorage) {
      const userData = localStorage.getItem('user');


      if (userData && userData !== 'undefined' && userData !== 'null') {
        try {
          const parsedUser = JSON.parse(userData);

          if (parsedUser && typeof parsedUser === 'object' && parsedUser.email && parsedUser.role) {
            return parsedUser;
          } else {
            console.warn('Invalid user data structure in localStorage');
            localStorage.removeItem('user');
            return null;
          }
        } catch (error) {
          console.error('Error parsing user data from localStorage:', error);

          localStorage.removeItem('user');
          return null;
        }
      }
    }
    return null;
  }

  login(credentials: {email: string, password: string}): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/api/v1/auth/authenticate`, credentials);
  }

  register(userdata: {name: string, email: string, password: string, role: string}): Observable<any> {
    return this.http.post<RegisterRequest>(`${this.apiUrl}/api/v1/auth/register`, userdata);
  }

  saveUserData(response: LoginResponse): void {
    if (response?.token && response?.user) {
      localStorage.setItem('token', response.token);
      localStorage.setItem('user', JSON.stringify(response.user));
      this.currentUserSubject.next(response.user);
    } else {
      console.error('Invalid response data for saving user');
    }
  }

  isLoggedIn(): boolean {
    const user = this.getCurrentUser();
    const isLoggedIn = !!user;
    return isLoggedIn;
  }

  getCurrentUser(): User | null {
    const user = this.currentUserSubject.value;
    return user;
  }

  logout(): void {
    console.log('Logout appelé');
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    this.currentUserSubject.next(null);
    console.log('Données supprimées, currentUserSubject mis à null');
  }

  getRole(): string | null {
    const user = this.getCurrentUser();
    return user ? user.role : null;
  }

  saveToken(token: string): void {
    if (token && token !== 'undefined') {
      localStorage.setItem('auth_token', token);
    }
  }

  getToken(): string | null {
    const token = localStorage.getItem('auth_token');
    return (token && token !== 'undefined' && token !== 'null') ? token : null;
  }

  saveRole(role: string): void {
    if (role && role !== 'undefined') {
      localStorage.setItem('role', role);
    }
  }
  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }
}
