import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService, RegisterRequest} from "../services/auth.service";
import {Router, RouterLink} from "@angular/router"
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
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
      password:['',[Validators.required]],
      role:['',[Validators.required]]
    });


  }
  ngOnInit(): void {
    this.errorMessage='';
    this.successMessage='';
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log(this.registerForm.value);
      const data :  RegisterRequest= this.registerForm.value;
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
