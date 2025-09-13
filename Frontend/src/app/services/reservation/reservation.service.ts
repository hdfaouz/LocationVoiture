import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {  Observable } from "rxjs";

interface Reservation {
  id: number;
  startDate: Date;
  endDate: Date;
  totalPrice: number;
  status: string;
  clientId: number;
  voitureId: number;
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
    return this.http.get<Reservation[]>(`${this.apiUrl}/all`);
  }

  getMyReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/my-reservations`);
  }
  getAllRe(id: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/my/${id}`);
  }

  deleteReservation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  changeStatus(id: number, status: string): Observable<Reservation> {
    return this.http.patch<Reservation>(`${this.apiUrl}/statut/${id}?status=${status}`, {});
  }
}
