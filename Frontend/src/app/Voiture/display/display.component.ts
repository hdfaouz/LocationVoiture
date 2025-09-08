import {Component, OnInit} from '@angular/core';
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
export class DisplayComponent implements OnInit {
  cars: Car[] = [];
  errorMessage: string | null = null;

  constructor(private voitureService: VoitureService) {
  }

  ngOnInit(): void {
    console.log('ngOnInit appelé - Chargement des voitures...');
    this.loadCars();
  }

  loadCars(): void {
    console.log('Appel de getCars()');
    this.voitureService.getCars().subscribe({
      next: (cars) => {
        console.log('Voitures reçues:', cars);
        this.cars = cars || [];
        this.errorMessage = null;
      },
      error: (error) => {
        console.error('Erreur lors du chargement:', error);
        this.errorMessage = error.message || 'Failed to load cars. Please try again later.';
        this.cars = [];
      }
    });
  }


  onDelete(car: Car): void {
    const confirmation = confirm(
      `Êtes-vous sûr de vouloir supprimer la ${car.brand} ${car.model} ?`
    );

    if (!confirmation) {
      return;
    }

    console.log('Suppression de:', car);
    this.voitureService.deleteCar(car.id!).subscribe({
      next: (response) => {
        console.log('Suppression réussie:', response);
        this.cars = this.cars.filter(c => c.id !== car.id);
        alert(`${car.brand} ${car.model} a été supprimée avec succès !`);
      },
      error: (error) => {
        console.error('Erreur lors de la suppression:', error);
        alert(`Erreur : Impossible de supprimer ${car.brand} ${car.model}`);
      }
    });
  }
}


