import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {  Observable } from "rxjs";

export interface Reservation {
  carId: number;
  userId: string;
  startDate: Date;
  endDate: Date;
  totalPrice: number;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private apiUrl = 'http://localhost:8080/reservation';

  constructor(private http: HttpClient) { }


  addReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation);
  }

  getAll(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl);
  }
}
