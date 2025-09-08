import {Component} from '@angular/core';
import {ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css'
})
export class BookingComponent {

}
