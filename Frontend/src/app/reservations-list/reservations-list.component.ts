import { Component, OnDestroy, OnInit } from '@angular/core';
import { ReservationService } from '../services/reservation/reservation.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from "../services/auth.service";
import { interval, Subscription } from 'rxjs';

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
  selector: 'app-reservations-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservations-list.component.html',
  styleUrls: ['./reservations-list.component.css']
})
export class ReservationsListComponent implements OnInit, OnDestroy {

  reservations: Reservation[] = [];
  isAdmin: boolean = false;
  private pollingSubscription?: Subscription;

  constructor(private reservationService: ReservationService, private router: Router, private auth: AuthService) {
    this.isAdmin = this.auth.isAdmin();
  }

  ngOnInit(): void {
    this.loadReservations();
    if (!this.isAdmin) {
      this.startPolling();
    }
  }

  ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }

  startPolling(): void {
    this.pollingSubscription = interval(10000)
      .subscribe(() => {
        this.checkForUpdates();
      });
  }

  checkForUpdates(): void {
    this.reservationService.getMyReservations().subscribe(newReservations => {
      if (this.reservations && this.reservations.length > 0) {
        newReservations.forEach(newRes => {
          const oldRes = this.reservations.find(r => r.id === newRes.id);
          if (oldRes && oldRes.status === 'EN_ATTENTE' && newRes.status !== 'EN_ATTENTE') {
            alert(`Le statut de votre réservation pour la voiture ${newRes.voitureId} est passé à : ${newRes.status}`);
          }
        });
      }
      this.reservations = newReservations;
    });
  }

  loadReservations(): void {
    if (this.isAdmin) {
      this.reservationService.getAll()
        .subscribe(reservations => {
          this.reservations = reservations;
        });
    } else {
      this.reservationService.getMyReservations()
        .subscribe(reservations => {
          this.reservations = reservations;
        });
    }
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

  onConfirm(id: number): void {
    this.reservationService.changeStatus(id, 'CONFIRMEE').subscribe(updatedReservation => {
      const index = this.reservations.findIndex(r => r.id === id);
      if (index !== -1) {
        this.reservations[index] = updatedReservation;
      }
    });
  }

  onRefuse(id: number): void {
    this.reservationService.changeStatus(id, 'REFUSEE').subscribe(updatedReservation => {
      const index = this.reservations.findIndex(r => r.id === id);
      if (index !== -1) {
        this.reservations[index] = updatedReservation;
      }
    });
  }
}
