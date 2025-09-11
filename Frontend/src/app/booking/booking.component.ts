import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservationService } from '../services/reservation/reservation.service';
import { FormsModule } from '@angular/forms';
import {VoitureService} from "../services/voiture/voiture.service";
import {Car} from "../Voiture/add/add.component";

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css'
})
export class BookingComponent implements OnInit {

  reservation: any = {
    startDate: new Date(),
    endDate: new Date(),
    totalPrice: 0,
    status: 'CONFIRMEE'
  };


  voitureId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reservationService: ReservationService,
    private voiture: VoitureService
  ) { }

  prix !: number
  ngOnInit(): void {
    this.voitureId = this.route.snapshot.params['id'];
    this.voiture.getCarById(this.voitureId)
      .subscribe((car: Car) =>{
        this.prix = car.pricePerDay


      })

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
  calcul(){



    const date1 : Date= new Date(this.reservation.startDate);
    const date2 : Date= new Date(this.reservation.endDate);

    const diffTime = +Math.abs(date2.getTime() - date1.getTime());
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    this.reservation.totalPrice = diffDays * this.prix

  }

}
