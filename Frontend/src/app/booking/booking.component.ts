import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservationService } from '../services/reservation/reservation.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css'
})
export class BookingComponent implements OnInit {

  reservation: any = {
    startDate: '2025-07-01',
    endDate: '2025-07-10',
    totalPrice: 450.0,
    status: 'CONFIRMEE'
  };
  voitureId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reservationService: ReservationService
  ) { }

  ngOnInit(): void {
    this.voitureId = this.route.snapshot.params['id'];
  }

  addReservation(): void {
    this.reservation.voitureId = this.voitureId;
    this.reservation.clientId = localStorage.getItem('userId');
    this.reservation.status = 'EN_ATTENTE';

    this.reservationService.addReservation(this.reservation)
      .subscribe(() => {
        this.router.navigate(['/reservations']);
      });
  }
}
