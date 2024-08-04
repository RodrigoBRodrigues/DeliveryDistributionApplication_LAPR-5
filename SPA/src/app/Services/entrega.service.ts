import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable, of} from 'rxjs';
import { Entrega } from '../Model/entrega';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EntregaService {

  constructor(private http:HttpClient) { }

  private backUrl= environment.dotNetUrl+'/api/entregas/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})  // constructs a new HTTP header object which accepts JSON data
  }


  addEntrega(armazem: Entrega): Observable<Entrega> { //create new Entrega
    return this.http.post<Entrega>(
    this.backUrl, armazem,
    this.httpOptions);
  }

  getEntregas(): Observable<Entrega[]> {
    return this.http.get<Entrega[]>(this.backUrl,this.httpOptions);
  }


  getEntregasDia(data:string):Observable<Entrega[]> {
    const newUrl = this.backUrl.concat("dates/?startdate=").concat(data).concat("T00:00:00&finishdate=").concat(data).concat("T23:59:59");
    return this.http.get<Entrega[]>(newUrl,this.httpOptions);
  }

}
