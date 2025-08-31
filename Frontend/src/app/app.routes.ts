import { Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {HomeComponent} from "./home/home.component";
import {AddComponent} from "./Voiture/add/add.component";
import {BookingComponent} from "./booking/booking.component";
import {DetailsComponent} from "./details/details.component";
import {ContactUsComponent} from "./contact-us/contact-us.component";

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path : 'register' , component: RegisterComponent},
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {path: 'home', component: HomeComponent},
  {path: 'add', component:AddComponent},
  {path: 'book',component: BookingComponent},
  {path: 'detail',component:DetailsComponent},
  {path:'contact', component:ContactUsComponent}
];
