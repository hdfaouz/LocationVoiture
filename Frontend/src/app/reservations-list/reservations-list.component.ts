import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../services/reservation/reservation.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-reservations-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservations-list.component.html',
  styleUrls: ['./reservations-list.component.css']
})
export class ReservationsListComponent implements OnInit {

  reservations: any[] = [];
  isAdmin: boolean = false;

  constructor(private reservationService: ReservationService, private router: Router, private auth : AuthService ) { 
    this.isAdmin = this.auth.isAdmin();
  }

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.reservationService.getAll()
      .subscribe(reservations => {
        this.reservations = reservations;
      });
  }

  onEdit(id: number): void {
    console.log('Editing reservation with id:', id);

  }

  onDelete(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette réservation ?')) {
      this.reservationService.deleteReservation(id).subscribe(() => {
        this.reservations = this.reservations.filter(reservation => reservation.id !== id);
      }, error => {
        console.error('Error deleting reservation:', error);
      });
    }
  }
}
