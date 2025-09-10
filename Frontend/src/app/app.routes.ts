import { Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {HomeComponent} from "./home/home.component";
import {AddComponent} from "./Voiture/add/add.component";
import {BookingComponent} from "./booking/booking.component";
import {DetailsComponent} from "./details/details.component";
import {ContactUsComponent} from "./contact-us/contact-us.component";
import {AboutUsComponent} from "./about-us/about-us.component";
import {DisplayComponent} from "./Voiture/display/display.component";
import {authGuard} from "./guards/auth.guard";
import { ReservationsListComponent } from './reservations-list/reservations-list.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },


  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'cars', component: DisplayComponent, canActivate: [authGuard] },
  { path: 'add', component: AddComponent, canActivate: [authGuard] },
  { path: 'booking/:id', component: BookingComponent, canActivate: [authGuard] },
  { path: 'detail/:id', component: DetailsComponent, canActivate: [authGuard] },
  { path: 'contact', component: ContactUsComponent, canActivate: [authGuard] },
  { path: 'about', component: AboutUsComponent, canActivate: [authGuard] },
  { path: 'reservations', component: ReservationsListComponent, canActivate: [authGuard] },


  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
