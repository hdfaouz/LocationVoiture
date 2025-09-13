import {Component, OnInit} from '@angular/core';
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from "@angular/common";
import {ReservationService} from "../services/reservation/reservation.service";

interface Reservation {
  id: number;
  startDate: Date;
  endDate: Date;
  totalPrice: number;
  status: string;
  clientId: number;
  voitureId: number;
}
@Component({
  selector: 'app-reservation-a',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    NgForOf,

  ],
  templateUrl: './reservation-a.component.html',
  styleUrl: './reservation-a.component.css'
})
export class ReservationAComponent implements OnInit{
  reservations !: Reservation[] ;
  constructor(private reservationService : ReservationService) {
  }
  ngOnInit(): void {

  }

  loadReservation(id: number): void {
    this.reservationService.getAllRe(id)
      .subscribe(reservations => {
        this.reservations = reservations;
      });
  }

}
