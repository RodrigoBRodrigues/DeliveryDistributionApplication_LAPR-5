import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable, of} from 'rxjs';
import { environment } from 'src/environments/environment';
import { Viagem } from '../Model/viagem';

@Injectable({
  providedIn: 'root'
})
export class ViagemService {

  constructor(private http:HttpClient) { }

  private backUrl= environment.nodeUrl+'/api/planeamento/all';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})  // constructs a new HTTP header object which accepts JSON data
  }

  getViagens(): Observable<Viagem[]> {
    return this.http.get<Viagem[]>(this.backUrl,this.httpOptions);
  }
}
