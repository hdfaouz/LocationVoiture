import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../services/reservation/reservation.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reservations-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservations-list.component.html',
  styleUrls: ['./reservations-list.component.css']
})
export class ReservationsListComponent implements OnInit {

  reservations: any[] = [];

  constructor(private reservationService: ReservationService) { }

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.reservationService.getAll()
      .subscribe(reservations => {
        this.reservations = reservations;
      });
  }

}
