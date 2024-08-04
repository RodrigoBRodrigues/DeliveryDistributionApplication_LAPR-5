import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PlaneamentoService {
  constructor(private http: HttpClient) {}
  response: string = '';

  private backUrl = environment.nodeUrl + '/api/planeamento';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
    }), // constructs a new HTTP header object which accepts JSON data
  };

  getSolucaoOtima(camiao: string, dia: string): Observable<string[]> {
    const json = '{"dia":"' + dia + '","camiao":"' + camiao + '"}';
    console.log(json);
    return this.http.post<string[]>(this.backUrl, json, this.httpOptions);
  }
  getHeu1(camiao: string, dia: string): Observable<string[]> {
    console.log(this.backUrl);
    const json = '{"dia":' + dia + ',"camiao":"' + camiao + '"}';
    console.log(json);
    const newUrl = this.backUrl.concat('/heuristica1');
    return this.http.post<string[]>(newUrl, json, this.httpOptions);
  }
  getHeu2(camiao: string, dia: string): Observable<string[]> {
    const json = '{"dia":' + dia + ',"camiao":"' + camiao + '"}';
    console.log(json);
    const newUrl = this.backUrl.concat('/heuristica2');
    return this.http.post<string[]>(newUrl, json, this.httpOptions);
  }
  getHeu3(camiao: string, dia: string): Observable<string[]> {
    const json = '{"dia":' + dia + ',"camiao":"' + camiao + '"}';
    console.log(json);
    const newUrl = this.backUrl.concat('/heuristica3');
    return this.http.post<string[]>(newUrl, json, this.httpOptions);
  }
  getHeuMock(camiao: string, dia: string): Observable<string[]> {
    const json = '{"dia":' + dia + ',"camiao":"' + camiao + '"}';
    console.log(json);
    const newUrl = this.backUrl.concat('/heuristicaMock');
    return this.http.get<string[]>(newUrl, this.httpOptions);
  }
  getAG(camiao: string, dia: string): Observable<string[]> {
    const json = '{"dia":' + dia + ',"camiao":"' + camiao + '"}';
    console.log(json);
    const newUrl = this.backUrl.concat('/algoritmogenetico');
    console.log(newUrl);
    return this.http.post<string[]>(newUrl,json, this.httpOptions);
  }
  getAG3(camiao: string, dia: string): Observable<string[]> {
    const json = '{"dia":' + dia + ',"camiao":"' + camiao + '"}';
    console.log(json);
    const newUrl = this.backUrl.concat('/algoritmogeneticofrota');
    console.log(newUrl);
    return this.http.post<string[]>(newUrl,json, this.httpOptions);
  }
}
