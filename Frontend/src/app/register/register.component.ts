import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService, RegisterRequest} from "../services/auth.service";
import {Router} from "@angular/router"
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  registerForm : FormGroup;
  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private router: Router){
    this.registerForm= this.formBuilder.group({
      name:['',[Validators.required]],
      email:['',[Validators.required]],
      password:['',[Validators.required]]
    });


  }
  ngOnInit(): void {
    this.errorMessage='';
    this.successMessage='';
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const formValue = this.registerForm.value;
      const data: RegisterRequest = {
        ...formValue,
        role: 'CLIENT'
      };
      this.authService.register(data).subscribe({
        next : (response) =>{
          this.successMessage = 'Registration successful';

          console.log('user:',response)
          this.errorMessage = '';
          this.registerForm.reset();

          setTimeout(()=> this.router.navigate(['/login']),1500);
        },
        error: err=>{
          this.errorMessage = err || 'Registration failed';
          this.successMessage='';
        }
      });

    }
  }

}
