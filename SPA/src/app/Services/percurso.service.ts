import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable, of} from 'rxjs';
import { Percurso } from '../Model/percurso';
import { catchError, retry } from 'rxjs/operators';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PercursoService {

  constructor(private http:HttpClient) { }

  private backUrl= environment.nodeUrl+'/api/percursos/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})  // constructs a new HTTP header object which accepts JSON data
  }


  addPercurso(percurso: Percurso): Observable<Percurso> { //create new Percurso
    return this.http.post<Percurso>(
    this.backUrl, percurso,
    this.httpOptions);
  }

  getPercursos(): Observable<Percurso[]> {
    return this.http.get<Percurso[]>(this.backUrl,this.httpOptions);
  }

  getAllPercursos(): Observable<Percurso[]> {
    return this.http.get<Percurso[]>(this.backUrl).pipe(
      catchError(this.handleError<Percurso[]>('getPercursos', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

}
