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
export class ArmazemService {
  constructor(private http:HttpClient) { }


  private backUrl= environment.dotNetUrl+'/api/armazens/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})  // constructs a new HTTP header object which accepts JSON data
  }

  patchAtivo(id: string, ativo: boolean): Observable<Armazem> {
    const newUrl = this.backUrl + id;
    //console.log(newUrl);
    const json = '{"ativo":' + ativo + '}"'
    return this.http.patch<Armazem>(
    newUrl, json,
    this.httpOptions);
  }

  addArmazem(armazem: Armazem): Observable<Armazem> {
    return this.http.post<Armazem>(
    this.backUrl, armazem,
    this.httpOptions);
  }

  getArmazens(): Observable<Armazem[]> {
    return this.http.get<Armazem[]>(this.backUrl,this.httpOptions);
  }

  getArmazensAtivos():Observable<Armazem[]> {
    const newUrl = this.backUrl + 'ativos=true';
    return this.http.get<Armazem[]>(newUrl,this.httpOptions);
  }
  
  getArmazemData(id:string): Observable<ArmazemData> {
    return this.http.get<ArmazemData>('https://localhost:5001/api/armazemAdjacencias/' + id,this.httpOptions);
  }


  getAllArmazens(): Observable<Armazem[]> {
    return this.http.get<Armazem[]>(this.backUrl).pipe(
      catchError(this.handleError<Armazem[]>('getArmazens', []))
    );
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
