import { Injectable } from '@angular/core';
import {catchError, Observable, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Car} from "../../Voiture/add/add.component";

@Injectable({
  providedIn: 'root'
})
export class VoitureService {

  private apiUrl = 'http://localhost:8080/voiture';

  constructor(private http: HttpClient) {}


  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return headers;
  }


  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Une erreur est survenue';

    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erreur: ${error.error.message}`;
    } else {
      errorMessage = `Code d'erreur: ${error.status}, Message: ${error.message}`;

      if (error.status === 401) {
        errorMessage = 'Token expiré ou invalide. Veuillez vous reconnecter.';

      } else if (error.status === 403) {
        errorMessage = 'Accès refusé. Vous n\'avez pas les permissions nécessaires.';
      } else if (error.status === 404) {
        errorMessage = 'Ressource non trouvée.';
      }
    }

    console.error('Erreur HTTP:', error);
    return throwError(() => new Error(errorMessage));
  }

  addCar(car: Car): Observable<any> {
    return this.http.post<any>(this.apiUrl, car, {
      headers: this.getHeaders(),
      withCredentials: true // Important pour CORS
    }).pipe(
      catchError(this.handleError)
    );
  }

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiUrl, {
      headers: this.getHeaders(),
    }).pipe(
      catchError(this.handleError)
    );
  }

  getCarById(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
      withCredentials: true
    }).pipe(
      catchError(this.handleError)
    );
  }

  updateCar(id: string, car: Car): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, car, {
      headers: this.getHeaders(),
      withCredentials: true
    }).pipe(
      catchError(this.handleError)
    );
  }

  deleteCar(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
      withCredentials: true
    }).pipe(
      catchError(this.handleError)
    );
  }


  getCarsByCategory(category: string): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/category/${category}`, {
      headers: this.getHeaders(),
      withCredentials: true
    }).pipe(
      catchError(this.handleError)
    );
  }

  getAvailableCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/available`, {
      headers: this.getHeaders(),
      withCredentials: true
    }).pipe(
      catchError(this.handleError)
    );
  }

  searchCars(brand?: string, model?: string, category?: string): Observable<Car[]> {
    let params: string[] = [];

    if (brand) params.push(`brand=${encodeURIComponent(brand)}`);
    if (model) params.push(`model=${encodeURIComponent(model)}`);
    if (category) params.push(`category=${encodeURIComponent(category)}`);

    const queryString = params.length > 0 ? `?${params.join('&')}` : '';

    return this.http.get<Car[]>(`${this.apiUrl}/search${queryString}`, {
      headers: this.getHeaders(),
      withCredentials: true
    }).pipe(
      catchError(this.handleError)
    );
  }


  refreshToken(): Observable<any> {
    const refreshToken = localStorage.getItem('refreshToken');
    return this.http.post<any>('http://localhost:8080/api/auth/refresh',
      { token: refreshToken },
      { withCredentials: true }
    ).pipe(
      catchError(this.handleError)
    );
  }



}
