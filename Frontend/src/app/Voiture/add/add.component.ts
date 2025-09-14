import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {VoitureService} from "../../services/voiture/voiture.service";
import {CommonModule} from "@angular/common";
import {RouterLink} from "@angular/router";


export interface Car {
  id: string;
  brand: string;
  model: string;
  category: string;
  pricePerDay: number;
  available: boolean;
  imageUrl: string;
}
@Component({
  selector: 'app-add',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,

  ],
  templateUrl: './add.component.html',
  styleUrl: './add.component.css'
})
export class AddComponent {
  carForm: FormGroup;
  isSubmitting = false;
  submitSuccess = false;
  errorMessage = '';

  categories = [
    { value: 'berline', label: 'Berline' },
    { value: 'suv', label: 'SUV' },
    { value: 'coupe', label: 'Coupé' },
    { value: 'cabriolet', label: 'Cabriolet' },
    { value: 'break', label: 'Break' },
    { value: 'citadine', label: 'Citadine' },
    { value: 'monospace', label: 'Monospace' },
    { value: '4x4', label: '4x4' },
    { value: 'sportive', label: 'Sportive' },
    { value: 'luxe', label: 'Luxe' }
  ];

  constructor(
    private fb: FormBuilder,
    private carService: VoitureService
  ) {
    this.carForm = this.fb.group({
      brand: ['', [Validators.required, Validators.minLength(2)]],
      model: ['', [Validators.required, Validators.minLength(2)]],
      category: ['', [Validators.required]],
      pricePerDay: [0, [Validators.required, Validators.min(1)]],
      available: [true],
      imageUrl: ['']
    });
  }

  get f() { return this.carForm.controls; }

  onSubmit() {
    if (this.carForm.valid) {
      this.isSubmitting = true;
      this.errorMessage = '';

      const carData: Car = {
        ...this.carForm.value,
        pricePerDay: Number(this.carForm.value.pricePerDay) // Conversion en number
      };

      console.log('Données envoyées:', carData); // Debug

      this.carService.addCar(carData).subscribe({
        next: (response) => {
          console.log('Voiture ajoutée:', response);
          this.submitSuccess = true;
          this.carForm.reset();
          this.carForm.patchValue({ available: true, pricePerDay: 0 });

          setTimeout(() => {
            this.submitSuccess = false;
          }, 3000);
        },
        error: (error) => {
          console.error('Erreur complète:', error);
          this.errorMessage = error.error?.message || 'Erreur lors de l\'ajout de la voiture';
          this.isSubmitting = false;
        },
        complete: () => {
          this.isSubmitting = false;
        }
      });
    } else {
      // Marquer tous les champs comme touchés pour afficher les erreurs
      Object.keys(this.carForm.controls).forEach(key => {
        this.carForm.get(key)?.markAsTouched();
      });
    }
  }


  hasError(controlName: string, errorName: string): boolean {
    const control = this.carForm.get(controlName);
    return control ? control.hasError(errorName) && control.touched : false;
  }





}
