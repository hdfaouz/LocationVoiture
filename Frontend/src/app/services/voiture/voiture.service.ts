import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
export interface Voiture {
   brand:String;
   model:String
   category:String;
   pricePerDay:number;
   available:boolean;
}
@Injectable({
  providedIn: 'root'
})
export class VoitureService {

  private apiUrl = 'http://localhost:8080/voiture'

  constructor(private http :HttpClient) { }

  addVoiture(voiture:Voiture):Observable<Voiture>{
    return this.http.post<Voiture>(this.apiUrl,voiture)
  }

}
