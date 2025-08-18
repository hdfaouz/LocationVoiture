import {Component, OnInit} from '@angular/core';
import {Voiture, VoitureService} from "../../services/voiture/voiture.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-add',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './add.component.html',
  styleUrl: './add.component.css'
})
export class AddComponent implements OnInit{
  voitureForm!: FormGroup;
  successMessage: string | null = null;

  constructor(private voitureService: VoitureService, private fb: FormBuilder) { }

  ngOnInit(): void {
    // Initialisation du formulaire réactif avec FormBuilder
    this.voitureForm = this.fb.group({
      brand: ['', Validators.required],
      model: ['', Validators.required],
      category: ['', Validators.required],
      pricePerDay: [0, [Validators.required, Validators.min(0)]],
      available: [true]
    });
  }

  OnSubmit(): void {
    if (this.voitureForm.valid) {
      const newVoiture: Voiture = this.voitureForm.value as Voiture;

      this.voitureService.addVoiture(newVoiture).subscribe({
        next: (response) => {
          console.log('Nouvelle voiture ajoutée :', response);
          this.successMessage = `Voiture ${response.brand} ${response.model} ajoutée avec succès !`;
          this.voitureForm.reset({ available: true }); // Réinitialise le formulaire, en gardant 'available' à true
          setTimeout(() => this.successMessage = null, 5000);
        },
        error: (error) => {
          console.error('Erreur lors de l\'ajout de la voiture :', error);
          this.successMessage = `Erreur : Impossible d'ajouter la voiture. Veuillez réessayer.`;
          setTimeout(() => this.successMessage = null, 5000);
        }
      });
    } else {
      console.error('Le formulaire est invalide');
      this.voitureForm.markAllAsTouched(); // Affiche les erreurs de validation
    }
  }
}
