import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable, of} from 'rxjs';
import { Armazem } from '../Model/armazem';
import { catchError, retry } from 'rxjs/operators';
import { ArmazemData } from '../Model/armazemData';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArmazemDataService {
  constructor(private http:HttpClient) { }


  private backUrl= environment.dotNetUrl+'/api/entregasinfo';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})  // constructs a new HTTP header object which accepts JSON data
  }


  addArmazem(armazem: Armazem): Observable<Armazem> {
    return this.http.post<Armazem>(
    this.backUrl, armazem,
    this.httpOptions);
  }



  getArmazemData(): Observable<ArmazemData[]> {
    return this.http.get<ArmazemData[]>(this.backUrl,this.httpOptions);
  }




  /**
 * Handle Http operation that failed.
 * Let the app continue.
 *
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
