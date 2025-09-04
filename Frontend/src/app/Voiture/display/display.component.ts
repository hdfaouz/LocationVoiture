import { Component } from '@angular/core';
import {VoitureService} from "../../services/voiture/voiture.service";
import {Car} from "../add/add.component";
import {CurrencyPipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-display',
  standalone: true,
  imports: [
    NgIf,
    CurrencyPipe,
    NgForOf
  ],
  templateUrl: './display.component.html',
  styleUrl: './display.component.css'
})
export class DisplayComponent {
  cars: Car[] = [];
  errorMessage: string | null = null;

  constructor(private voitureService: VoitureService) {}

  ngOnInit(): void {
    this.loadCars();
  }

  loadCars(): void {
    this.voitureService.getCars().subscribe({
      next: (cars) => {
        this.cars = cars;
        this.errorMessage = null;
      },
      error: (error) => {
        this.errorMessage = error.message || 'Failed to load cars. Please try again later.';
        console.error('Error loading cars:', error);
      }
    });
  }

}
