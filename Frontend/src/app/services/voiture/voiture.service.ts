import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Car} from "../../Voiture/add/add.component";

@Injectable({
  providedIn: 'root'
})
export class VoitureService {

  private apiUrl = 'http://localhost:8080/voiture';

  constructor(private http: HttpClient) {}

  addCar(car: Car): Observable<any> {
    const headers = new HttpHeaders({

      'Content-Type': 'application/json'
    });

    return this.http.post<any>(this.apiUrl, car, { headers });
  }

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiUrl);
  }

  getCarById(id: string): Observable<Car> {
    return this.http.get<Car>(`${this.apiUrl}/${id}`);
  }

  updateCar(id: string, car: Car): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.put<any>(`${this.apiUrl}/${id}`, car, { headers });
  }

  deleteCar(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Méthodes spécifiques aux voitures
  getCarsByCategory(category: string): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/category/${category}`);
  }

  getAvailableCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/available`);
  }

  searchCars(brand?: string, model?: string, category?: string): Observable<Car[]> {
    let params = '';
    const filters = [];

    if (brand) filters.push(`brand=${encodeURIComponent(brand)}`);
    if (model) filters.push(`model=${encodeURIComponent(model)}`);
    if (category) filters.push(`category=${encodeURIComponent(category)}`);

    if (filters.length > 0) {
      params = '?' + filters.join('&');
    }

    return this.http.get<Car[]>(`${this.apiUrl}/search${params}`);
  }



}
